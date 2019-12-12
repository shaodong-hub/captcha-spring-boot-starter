//package com.github.verification.captcha.config;
//
//import com.github.verification.captcha.common.CaptchaUtil;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * <p>
// * 创建时间为 下午6:55 2019/12/11
// * 项目名称 captcha-spring-boot-starter
// * </p>
// *
// * @author 石少东
// * @version 0.0.1
// * @since 0.0.1
// */
//
//
//@Component
//public class ConfigCaptchaUtilBean {
//
//    @Resource
//    private ConfigCaptchaProperty property;
//
//    @Bean
//    public CaptchaUtil captchaUtil() {
//        return CaptchaUtil.builder()
//                .size(property.getSize())
//                .fontSize(property.getFontSize())
//                .width(property.getWidth())
//                .height(property.getHeight())
//                .lines(property.getLines())
//                .tilt(property.getTilt())
//                .backgroundColor(property.getBackgroundColor())
//                .build();
//    }
//
//}
