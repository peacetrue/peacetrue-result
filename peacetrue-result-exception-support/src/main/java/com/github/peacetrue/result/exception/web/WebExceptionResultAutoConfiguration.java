package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.exception.ExceptionConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * for web exception convert
 *
 * @author xiayx
 */
@Configuration
public class WebExceptionResultAutoConfiguration {

    @Bean
    public ExceptionConverter missingServletRequestParameterExceptionConverter() {
        return new MissingServletRequestParameterExceptionConverter();
    }

    @Bean
    public ExceptionConverter missingPathVariableExceptionConverter() {
        return new MissingPathVariableExceptionConverter();
    }

    @Bean
    public ExceptionConverter methodArgumentTypeMismatchExceptionConverter() {
        return new MethodArgumentTypeMismatchExceptionConverter();
    }

    @Bean
    public ExceptionConverter methodArgumentConversionNotSupportedExceptionConverter() {
        return new MethodArgumentConversionNotSupportedExceptionConverter();
    }

    @Bean
    public ExceptionConverter bindExceptionConverter() {
        return new BindExceptionConverter();
    }

//    @Bean
//    public ExceptionConverter methodArgumentNotValidExceptionConverter() {
//        return new MethodArgumentNotValidExceptionConverter();
//    }

//    @Bean
//    @ConditionalOnMissingBean(name = "resourceAccessExceptionConverter")
//    public ResourceAccessExceptionConverter resourceAccessExceptionConverter() {
//        return new ResourceAccessExceptionConverter();
//    }


}
