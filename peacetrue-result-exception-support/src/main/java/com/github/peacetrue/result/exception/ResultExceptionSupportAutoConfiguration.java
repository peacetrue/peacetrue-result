package com.github.peacetrue.result.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractResourceBasedMessageSource;

/**
 * 响应结果异常支持自动配置。
 *
 * @author peace
 **/
@Configuration
@EnableConfigurationProperties(ResultExceptionSupportProperties.class)
public class ResultExceptionSupportAutoConfiguration {

    private final ResultExceptionSupportProperties properties;

    public ResultExceptionSupportAutoConfiguration(ResultExceptionSupportProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ResultCodeClassifier resultCodeClassifier() {
        return new ConfiguredResultCodeClassifier(properties.getClassifiedCodes());
    }

    @Autowired
    public void registerMessageSourceBasename(AbstractResourceBasedMessageSource messageSource) {
        messageSource.addBasenames("peacetrue-result-exception-support");
    }

}
