package com.github.peacetrue.result.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractResourceBasedMessageSource;

/**
 * 响应结果异常支持自动配置。
 *
 * @author peace
 **/
@Configuration
@AutoConfigureAfter(ResultExceptionAutoConfiguration.class)
public class ResultExceptionSupportAutoConfiguration {

    @Autowired
    public void registerMessageSourceBasename(AbstractResourceBasedMessageSource messageSource) {
        messageSource.addBasenames("peacetrue-result-exception-support");
    }

}
