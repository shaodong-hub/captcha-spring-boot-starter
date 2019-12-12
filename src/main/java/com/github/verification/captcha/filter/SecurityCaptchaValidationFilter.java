package com.github.verification.captcha.filter;

import com.github.verification.captcha.common.CaptchaUtil;
import com.github.verification.captcha.config.ConfigCaptchaProperty;
import com.github.verification.captcha.config.ConfigSecurityConstant;
import com.github.verification.captcha.exception.CaptchaNotValidException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
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
@Component
public class SecurityCaptchaValidationFilter extends OncePerRequestFilter {

    @Resource
    private ConfigCaptchaProperty property;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        if (property.getCheckPaths().contains(request.getRequestURI()) && StringUtils.equalsIgnoreCase(request.getMethod(), ConfigSecurityConstant.POST)) {
            validate(new ServletWebRequest(request));
        }
        filterChain.doFilter(request, response);
    }

    @SneakyThrows(CaptchaNotValidException.class)
    private void validate(@NotNull ServletWebRequest servletWebRequest) {
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

}
