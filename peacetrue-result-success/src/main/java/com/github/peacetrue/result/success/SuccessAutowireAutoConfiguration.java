package com.github.peacetrue.result.success;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 成功响应结果自动配置
 *
 * @author peace
 */
@Configuration
@EnableConfigurationProperties(SuccessAutowireProperties.class)
public class SuccessAutowireAutoConfiguration {

    @Autowired
    public void registerMessageSourceBasename(ResourceBundleMessageSource messageSource) {
        messageSource.addBasenames("peacetrue-result-success");
    }

    /** 支持 WebMvc */
    @Configuration
    @ConditionalOnClass(WebMvcConfigurer.class)
    public static class WebMvcSuccessAutowireAutoConfiguration {
        @Bean
        @ConditionalOnMissingBean(SuccessAutowireResponseBodyAdvice.class)
        public SuccessAutowireResponseBodyAdvice successAutowireResponseBodyAdvice(SuccessAutowireProperties properties) {
            return new SuccessAutowireResponseBodyAdvice(properties.getDisabledMethods());
        }
    }

}
