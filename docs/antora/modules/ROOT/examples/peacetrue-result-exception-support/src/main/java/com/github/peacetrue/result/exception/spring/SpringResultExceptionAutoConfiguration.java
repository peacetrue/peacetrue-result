package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.exception.ResultExceptionSupportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.FrameworkServlet;

/**
 * Spring 相关的异常响应结果自动配置。
 *
 * @author peace
 */
@Configuration
@ConditionalOnClass(FrameworkServlet.class)
@AutoConfigureAfter(ResultExceptionSupportAutoConfiguration.class)
public class SpringResultExceptionAutoConfiguration {

    @Bean
    public MissingServletRequestParameterExceptionConverter missingServletRequestParameterExceptionConverter() {
        return new MissingServletRequestParameterExceptionConverter();
    }

    @Bean
    public MissingPathVariableExceptionConverter missingPathVariableExceptionConverter() {
        return new MissingPathVariableExceptionConverter();
    }

    @Bean
    public MethodArgumentConversionNotSupportedExceptionConverter methodArgumentConversionNotSupportedExceptionConverter() {
        return new MethodArgumentConversionNotSupportedExceptionConverter();
    }

    @Bean
    public MethodArgumentTypeMismatchExceptionConverter methodArgumentTypeMismatchExceptionConverter() {
        return new MethodArgumentTypeMismatchExceptionConverter();
    }

    @Bean
    public TypeMismatchExceptionConverter typeMismatchExceptionConverter() {
        return new TypeMismatchExceptionConverter();
    }

    @Bean
    public BindExceptionConverter bindExceptionConverter() {
        return new BindExceptionConverter();
    }

    @Bean
    public HttpMessageNotReadableExceptionConverter httpMessageNotReadableExceptionConverter() {
        return new HttpMessageNotReadableExceptionConverter();
    }

    @Bean
    public MethodArgumentNotValidExceptionConverter methodArgumentNotValidExceptionConverter() {
        return new MethodArgumentNotValidExceptionConverter();
    }

}
