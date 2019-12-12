package com.github.verification.captcha.service.impl;

import com.github.verification.captcha.common.CaptchaUtil;
import com.github.verification.captcha.config.ConfigSecurityConstant;
import com.github.verification.captcha.service.ICaptchaService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 * 创建时间为 下午6:19 2019/12/11
 * 项目名称 captcha-spring-boot-starter
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@Slf4j
@Service
public class CaptchaServiceImpl implements ICaptchaService {

    @Resource
    private CaptchaUtil captchaUtil;

    @SneakyThrows(IOException.class)
    @Override
    public void getCaptcha(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        // 获取到 session
        HttpSession session = request.getSession();
        // 利用图片工具生成图片
        CaptchaUtil.Captcha captcha = captchaUtil.createImage();
        // 打印验证码
        log.debug("验证码:{}", captcha.getCaptcha());
        session.setAttribute(ConfigSecurityConstant.SESSION_CAPTCHA_CODE, captcha);
        // 将图片输出给浏览器
        BufferedImage image = captcha.getImage();
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    @Override
    public boolean checkCode(String code, @NotNull HttpServletRequest request) {
        HttpSession session = request.getSession();
        CaptchaUtil.Captcha captcha = (CaptchaUtil.Captcha) session.getAttribute(ConfigSecurityConstant.SESSION_CAPTCHA_CODE);
        return StringUtils.equalsIgnoreCase(code, captcha.getCaptcha());
    }
}
