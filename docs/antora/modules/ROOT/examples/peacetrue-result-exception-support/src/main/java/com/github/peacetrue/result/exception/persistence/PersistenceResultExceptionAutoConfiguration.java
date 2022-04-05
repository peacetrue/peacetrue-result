package com.github.peacetrue.result.exception.persistence;

import com.github.peacetrue.result.exception.ResultExceptionSupportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * for exception in artifact 'javax.persistence:javax.persistence-api:*'.
 *
 * @author peace
 **/
@Configuration
@AutoConfigureAfter(ResultExceptionSupportAutoConfiguration.class)
@ConditionalOnClass(EntityManager.class)
public class PersistenceResultExceptionAutoConfiguration {

    @Bean
    public EntityNotFoundExceptionConverter entityNotFoundExceptionConverter() {
        return new EntityNotFoundExceptionConverter();
    }

}
