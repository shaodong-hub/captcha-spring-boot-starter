package com.github.verification.captcha.service;

import com.github.verification.captcha.exception.CaptchaNotValidException;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * <p>
 * 创建时间为 下午1:29 2019/12/12
 * 项目名称 captcha-spring-boot-starter
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

public interface ICaptchaNotValidService {

    void captchaNotValid(ServletWebRequest request, CaptchaNotValidException e);

}
