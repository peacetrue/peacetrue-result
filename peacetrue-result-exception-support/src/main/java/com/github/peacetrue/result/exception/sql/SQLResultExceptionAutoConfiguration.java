package com.github.peacetrue.result.exception.sql;

import com.github.peacetrue.result.exception.ClassifiedResultCodeRegistry;
import com.github.peacetrue.result.exception.ExceptionResultAutoConfiguration;
import com.github.peacetrue.result.exception.NestExceptionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

/**
 * SQL 相关的异常响应结果自动配置。
 *
 * @author peace
 */
@Configuration
@AutoConfigureAfter(ExceptionResultAutoConfiguration.class)
@EnableConfigurationProperties(SQLResultExceptionProperties.class)
public class SQLResultExceptionAutoConfiguration {

    private final SQLResultExceptionProperties properties;

    public SQLResultExceptionAutoConfiguration(SQLResultExceptionProperties properties) {
        this.properties = properties;
    }

    @Bean
    public SQLConditionalExceptionConverter sqlExceptionConverter() {
        return new SQLConditionalExceptionConverter(properties.getMessagePatterns());
    }

    @Autowired
    public void registerNestExceptions(NestExceptionRegistry registry) {
        Stream.of(
                "org.springframework.dao.DataIntegrityViolationException",
                "org.springframework.dao.DuplicateKeyException",
                "org.hibernate.exception.ConstraintViolationException",
                "javax.persistence.PersistenceException"
        )
                .forEach(registry::registerNestException);
    }

    @Autowired
    public void registerDefaultClassifiedResultCode(ClassifiedResultCodeRegistry registry) {
        registry.registerClassifiedResultCode("unique", "SQL_23000", "SQL_23505");
    }

}
