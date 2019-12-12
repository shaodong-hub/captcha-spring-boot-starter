package com.github.verification.captcha.event;

import lombok.Getter;

/**
 * <p>
 * 创建时间为 下午2:35 2019/12/12
 * 项目名称 captcha-spring-boot-starter
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

public class CaptchaFailureEvent extends AbstractCaptchaEvent {

    private static final long serialVersionUID = -6854029273829697489L;

    @Getter
    private String message;

    public CaptchaFailureEvent(Object source, String message) {
        super(source);
        this.message = message;
    }
}
