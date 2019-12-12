package com.github.verification.captcha.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.verification.captcha.exception.CaptchaNotValidException;
import com.github.verification.captcha.service.ICaptchaNotValidService;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 创建时间为 下午2:17 2019/12/12
 * 项目名称 captcha-spring-boot-starter
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */


public class DefaultCaptchaNotValidServiceImpl implements ICaptchaNotValidService {

    @Resource
    private ObjectMapper objectMapper;

    @SneakyThrows({IOException.class})
    @Override
    public void captchaNotValid(@NotNull ServletWebRequest request, CaptchaNotValidException e) {
        HttpServletResponse httpServletResponse = request.getResponse();
        assert httpServletResponse != null;
        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(e));
    }

}
