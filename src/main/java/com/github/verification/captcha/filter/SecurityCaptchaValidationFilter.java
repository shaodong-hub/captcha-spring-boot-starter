package com.github.verification.captcha.filter;

import com.github.verification.captcha.common.CaptchaUtil;
import com.github.verification.captcha.config.ConfigCaptchaProperty;
import com.github.verification.captcha.config.ConfigSecurityConstant;
import com.github.verification.captcha.event.CaptchaFailureEvent;
import com.github.verification.captcha.event.CaptchaSuccessEvent;
import com.github.verification.captcha.exception.CaptchaNotValidException;
import com.github.verification.captcha.service.ICaptchaNotValidService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * <p>
 * 创建时间为 下午5:18 2019/12/11
 * 项目名称 captcha-spring-boot-starter
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@Slf4j
public class SecurityCaptchaValidationFilter extends OncePerRequestFilter implements ApplicationContextAware {

    @Resource
    private ICaptchaNotValidService service;

    @Resource
    private ConfigCaptchaProperty property;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        if (property.getCheckPaths().contains(request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), ConfigSecurityConstant.POST)) {
            try {
                validate(new ServletWebRequest(request));
            } catch (CaptchaNotValidException e) {
                log.debug("验证码有误:{}", e.getMessage());
                applicationContext.publishEvent(new CaptchaFailureEvent(this, e.getMessage()));
                service.captchaNotValid(new ServletWebRequest(request, response), e);
                return;
            }
        }
        log.debug("验证码校验成功");
        applicationContext.publishEvent(new CaptchaSuccessEvent(this));
        filterChain.doFilter(request, response);
    }

    private void validate(@NotNull ServletWebRequest servletWebRequest) throws CaptchaNotValidException {
        String requestCaptcha = servletWebRequest.getParameter(property.getParamName());
        HttpSession session = servletWebRequest.getRequest().getSession();
        CaptchaUtil.Captcha sessionCaptcha = (CaptchaUtil.Captcha) session.getAttribute(ConfigSecurityConstant.SESSION_CAPTCHA_CODE);
        if (null == sessionCaptcha) {
            throw new CaptchaNotValidException("验证码失效!重新获取");
        }
        if (sessionCaptcha.isExpired()) {
            session.removeAttribute(ConfigSecurityConstant.SESSION_CAPTCHA_CODE);
            throw new CaptchaNotValidException("验证码过期!");
        }
        int maxRetry = sessionCaptcha.getMaxRetry();
        if (maxRetry < 1) {
            session.removeAttribute(ConfigSecurityConstant.SESSION_CAPTCHA_CODE);
            throw new CaptchaNotValidException("验证码超过尝试次数!");
        }
        sessionCaptcha.setMaxRetry(--maxRetry);
        session.setAttribute(ConfigSecurityConstant.SESSION_CAPTCHA_CODE, sessionCaptcha);
        String captcha = sessionCaptcha.getCaptcha();
        if (!StringUtils.equalsIgnoreCase(captcha, requestCaptcha)) {
            throw new CaptchaNotValidException("验证码错误!");
        }
        // 验证成功以后清除 session 中的验证码
        session.removeAttribute(ConfigSecurityConstant.SESSION_CAPTCHA_CODE);
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

