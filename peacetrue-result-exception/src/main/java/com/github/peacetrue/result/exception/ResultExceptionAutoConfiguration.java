package com.github.peacetrue.result.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractResourceBasedMessageSource;

/**
 * 响应结果异常自动配置。
 *
 * @author peace
 */
@Configuration
@EnableConfigurationProperties(ResultExceptionProperties.class)
public class ResultExceptionAutoConfiguration {

    private final ResultExceptionProperties properties;

    public ResultExceptionAutoConfiguration(ResultExceptionProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(name = "controllerExceptionHandler")
    @ConditionalOnProperty(value = "peacetrue.result.exception.enableExceptionHandler", matchIfMissing = true)
    public ControllerExceptionHandler controllerExceptionHandler() {
        return new ControllerExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionConvertService.class)
    public ExceptionConvertService exceptionConvertService() {
        return new ExceptionConvertServiceImpl();
    }

    @Bean
    public FallbackExceptionConverter fallbackExceptionConverter() {
        return new FallbackExceptionConverter();
    }

    @Bean
    public ResultExceptionConverter resultExceptionConverter() {
        return new ResultExceptionConverter();
    }

    @Bean
    public NestConditionalExceptionConverter nestConditionalExceptionConverter() {
        return new NestConditionalExceptionConverter(properties.getNestClasses());
    }

    @Bean
    @ConditionalOnMissingBean(ResultExceptionThrowService.class)
    public ResultExceptionThrowService exceptionThrowService() {
        return new ResultExceptionThrowServiceImpl();
    }

    @Autowired
    public void registerMessageSourceBasename(AbstractResourceBasedMessageSource messageSource) {
        messageSource.addBasenames("peacetrue-result-exception");
    }

}
