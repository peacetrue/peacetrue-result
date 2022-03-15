package com.github.peacetrue.result.success;

import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 成功响应结果自动配置
 *
 * @author peace
 */
@Configuration
@AutoConfigureAfter(ResultMessageSourceAutoConfiguration.class)
@EnableConfigurationProperties(SuccessAutowireProperties.class)
public class SuccessAutowireAutoConfiguration {

    @Autowired
    public void registerMessageSourceBasename(AbstractResourceBasedMessageSource messageSource) {
        messageSource.addBasenames("peacetrue-result-success");
    }

    /** 支持 WebMvc */
    @Slf4j
    @Configuration
    @ConditionalOnClass(WebMvcConfigurer.class)
    public static class WebMvcSuccessAutowireAutoConfiguration {
        @Bean
        @ConditionalOnMissingBean(SuccessAutowireResponseBodyAdvice.class)
        public SuccessAutowireResponseBodyAdvice successAutowireResponseBodyAdvice(SuccessAutowireProperties properties) {
            log.info("the SuccessAutowireResponseBodyAdvice is instance by @Bean rather than @ComponentScan");
            return new SuccessAutowireResponseBodyAdvice(properties.getDisabledMethods());
        }
    }

}
