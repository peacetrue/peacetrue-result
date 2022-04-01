package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.exception.NestExceptionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.servlet.FrameworkServlet;

/**
 * Spring 相关的异常响应结果自动配置。
 *
 * @author peace
 */
@Configuration
@ConditionalOnClass(FrameworkServlet.class)
public class SpringExceptionResultAutoConfiguration {

    @Bean
    public MissingServletRequestParameterExceptionConverter missingServletRequestParameterExceptionConverter() {
        return new MissingServletRequestParameterExceptionConverter();
    }

    @Bean
    public MissingPathVariableExceptionConverter missingPathVariableExceptionConverter() {
        return new MissingPathVariableExceptionConverter();
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
    public MethodArgumentConversionNotSupportedExceptionConverter methodArgumentConversionNotSupportedExceptionConverter() {
        return new MethodArgumentConversionNotSupportedExceptionConverter();
    }

    @Bean
    public BindExceptionConverter bindExceptionConverter() {
        return new BindExceptionConverter();
    }

    @Bean
    public MethodArgumentNotValidExceptionConverter methodArgumentNotValidExceptionConverter() {
        return new MethodArgumentNotValidExceptionConverter();
    }

    @Autowired
    public void registerNestExceptions(NestExceptionRegistry registry) {
        registry.registerNestException(HttpMessageNotReadableException.class);
    }

}
