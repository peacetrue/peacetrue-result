package com.github.peacetrue.result.exception;

import com.github.peacetrue.spring.expression.MessageExpressionAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

import static com.github.peacetrue.result.exception.StandardExceptionConvertService.FALLBACK_EXCEPTION_CONVERTER_BEAN_NAME;

/**
 * used to handler exception
 *
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(ExceptionResultProperties.class)
@AutoConfigureAfter(MessageExpressionAutoConfiguration.class)
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class ExceptionResultAutoConfiguration {

    private ExceptionResultProperties properties;

    public ExceptionResultAutoConfiguration(ExceptionResultProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionConvertService.class)
    public ExceptionConvertService exceptionConvertService() {
        return new StandardExceptionConvertService();
    }

    @Bean
    @ConditionalOnMissingBean(name = FALLBACK_EXCEPTION_CONVERTER_BEAN_NAME)
    public FallbackExceptionConverter fallbackExceptionConverter() {
        return new FallbackExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(name = "resultExceptionConverter")
    public ResultExceptionConverter resultExceptionConverter() {
        return new ResultExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(name = "proxyConditionalExceptionConverter")
    public ConditionalExceptionConverter proxyConditionalExceptionConverter() {
        return new ProxyConditionalExceptionConverter(properties.getProxyClasses());
    }

    @Bean
    @ConditionalOnMissingBean(name = "noPlaceholderConditionalExceptionConverter")
    public NoPlaceholderConditionalExceptionConverter noPlaceholderConditionalExceptionConverter() {
        return new NoPlaceholderConditionalExceptionConverter(new HashMap<>());
    }

    @Bean
    @ConditionalOnMissingBean(ErrorAttributes.class)
    public ErrorAttributes resultErrorAttributes() {
        return new ResultErrorAttributes();
    }

}
