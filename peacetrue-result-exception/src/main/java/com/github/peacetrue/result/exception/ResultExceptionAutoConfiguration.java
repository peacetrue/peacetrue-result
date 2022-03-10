package com.github.peacetrue.result.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.request.WebRequest;

/**
 * 响应结果异常自动配置
 *
 * @author peace
 */
@Configuration
public class ResultExceptionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ExceptionConvertService.class)
    public ExceptionConvertService exceptionConvertService() {
        return new ExceptionConvertServiceImpl();
    }

    @Bean
    public GenericExceptionConverter genericExceptionConverter() {
        return new GenericExceptionConverter();
    }

    @Bean
    public ResultExceptionConverter resultExceptionConverter() {
        return new ResultExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(ResultExceptionThrowService.class)
    public ResultExceptionThrowService exceptionThrowService() {
        return new ResultExceptionThrowServiceImpl();
    }


    @Autowired
    public void registerMessageSourceBasename(ResourceBundleMessageSource messageSource) {
        messageSource.addBasenames("peacetrue-result-exception");
    }

    @Configuration
    @ConditionalOnClass(WebRequest.class)
    @AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
    public static class ResultErrorAttributesConfiguration {
        @Bean
        @ConditionalOnMissingBean(ErrorAttributes.class)
        public ErrorAttributes resultErrorAttributes() {
            return new ResultErrorAttributes();
        }
    }

}
