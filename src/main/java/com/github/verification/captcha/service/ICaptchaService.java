package com.github.verification.captcha.service;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public interface ICaptchaService {

    void getCaptcha(HttpServletRequest request, HttpServletResponse response);

    boolean checkCode(String code, @NotNull HttpServletRequest request);

}
