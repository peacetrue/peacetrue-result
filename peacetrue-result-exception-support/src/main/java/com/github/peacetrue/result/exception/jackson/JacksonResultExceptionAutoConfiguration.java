package com.github.peacetrue.result.exception.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.exception.ResultExceptionSupportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson 相关的异常响应结果自动配置。
 *
 * @author peace
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureAfter(ResultExceptionSupportAutoConfiguration.class)
public class JacksonResultExceptionAutoConfiguration {

    @Bean
    public JsonParseExceptionConverter jsonParseExceptionConverter() {
        return new JsonParseExceptionConverter();
    }

    @Bean
    public InvalidFormatExceptionConverter invalidFormatExceptionConverter() {
        return new InvalidFormatExceptionConverter();
    }
}
