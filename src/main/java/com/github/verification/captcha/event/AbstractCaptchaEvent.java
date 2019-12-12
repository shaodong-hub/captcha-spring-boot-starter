package com.github.verification.captcha.event;

import org.springframework.context.ApplicationEvent;

/**
 * <p>
 * 创建时间为 下午2:33 2019/12/12
 * 项目名称 captcha-spring-boot-starter
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

public abstract class AbstractCaptchaEvent extends ApplicationEvent {

    public AbstractCaptchaEvent(Object source) {
        super(source);
    }

}
