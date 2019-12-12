package com.github.verification.captcha.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * 创建时间为 下午4:31 2019/12/11
 * 项目名称 captcha-spring-boot-starter
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "com.github.captcha")
public class ConfigCaptchaProperty {


    /**
     *
     */
    private String paramName = "imageCode";

    /**
     * 验证路径
     */
    private Set<String> checkPaths = new HashSet<>();

    /**
     * 过期时间,单位是秒
     */
    private Integer expireTime = 60;

    /**
     * 默认字符数量
     */
    private Integer size = 4;

    /**
     * 默认干扰线数量
     */
    private Integer lines = 2;

    /**
     * 默认宽度
     */
    private Integer width = 120;

    /**
     * 默认高度
     */
    private Integer height = 35;

    /**
     * 默认字体大小
     */
    private Integer fontSize = 25;

    /**
     * 默认字体倾斜
     */
    private Boolean tilt = true;

    /**
     * 背景颜色
     */
    private Color backgroundColor = Color.LIGHT_GRAY;

}
