package com.github.verification.captcha.exception;

/**
 * <p>
 * 创建时间为 下午5:19 2019/12/11
 * 项目名称 captcha-spring-boot-starter
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

public class CaptchaNotValidException extends Exception {

    private static final long serialVersionUID = 2333282580512794340L;

    private String message;

    public CaptchaNotValidException(String message) {
        super(message);
        this.message = message;
    }
}
