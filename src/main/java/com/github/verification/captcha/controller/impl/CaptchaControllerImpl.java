package com.github.verification.captcha.controller.impl;

import com.github.verification.captcha.controller.ICaptchaController;
import com.github.verification.captcha.service.ICaptchaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.github.verification.captcha.config.ConfigSecurityConstant.ACCESS_CAPTCHA_URI;

/**
 * GetMapping("${com.github.captcha.captcha-path:/validation/image}")
 * <p>
 * 创建时间为 下午6:18 2019/12/11
 * 项目名称 captcha-spring-boot-starter
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@RestController
public class CaptchaControllerImpl implements ICaptchaController {

    @Resource
    private ICaptchaService service;

    @GetMapping(value = ACCESS_CAPTCHA_URI)
    @Override
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        service.getCaptcha(request, response);
    }

}
