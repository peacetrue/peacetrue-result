package com.github.peacetrue.result.exception.persistence;

import com.github.peacetrue.result.exception.ResultExceptionSupportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * JPA 相关的异常响应结果自动配置。
 *
 * @author peace
 **/
@Configuration
@ConditionalOnClass(EntityManager.class)
@AutoConfigureAfter(ResultExceptionSupportAutoConfiguration.class)
public class PersistenceResultExceptionAutoConfiguration {

    @Bean
    public EntityNotFoundExceptionConverter entityNotFoundExceptionConverter() {
        return new EntityNotFoundExceptionConverter();
    }

}
