package com.github.verification.captcha.config;

import com.github.verification.captcha.common.CaptchaUtil;
import com.github.verification.captcha.controller.ICaptchaController;
import com.github.verification.captcha.controller.impl.CaptchaControllerImpl;
import com.github.verification.captcha.filter.SecurityCaptchaValidationFilter;
import com.github.verification.captcha.service.ICaptchaService;
import com.github.verification.captcha.service.impl.CaptchaServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * <p>
 * 创建时间为 下午4:30 2019/12/11
 * 项目名称 captcha-spring-boot-starter
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */


@Configuration
@EnableConfigurationProperties(ConfigCaptchaProperty.class)
public class CaptchaConfigurationSelector {

    @Resource
    private ConfigCaptchaProperty property;

    @Bean
    public CaptchaUtil captchaUtil() {
        return CaptchaUtil.builder()
                .size(property.getSize())
                .fontSize(property.getFontSize())
                .width(property.getWidth())
                .height(property.getHeight())
                .lines(property.getLines())
                .tilt(property.getTilt())
                .backgroundColor(property.getBackgroundColor())
                .build();
    }

    @Bean
    public ICaptchaService iCaptchaService() {
        return new CaptchaServiceImpl();
    }

    @Bean
    public ICaptchaController ICaptchaController() {
        return new CaptchaControllerImpl();
    }

    @Bean
    public SecurityCaptchaValidationFilter securityCaptchaValidationFilter(){
        return new SecurityCaptchaValidationFilter();
    }

}
