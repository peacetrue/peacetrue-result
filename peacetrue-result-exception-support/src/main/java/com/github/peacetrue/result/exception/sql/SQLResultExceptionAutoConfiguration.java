package com.github.peacetrue.result.exception.sql;

import com.github.peacetrue.result.exception.ClassifiedResultCodeRegistry;
import com.github.peacetrue.result.exception.NestExceptionRegistry;
import com.github.peacetrue.result.exception.ResultExceptionSupportAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

import static com.github.peacetrue.result.exception.sql.SQLDuplicateConditionalExceptionConverter.getDuplicateResultCode;

/**
 * SQL 相关的异常响应结果自动配置。
 *
 * @author peace
 */
@Configuration
@AutoConfigureAfter(ResultExceptionSupportAutoConfiguration.class)
public class SQLResultExceptionAutoConfiguration {

    @Bean
    public SQLDuplicateConditionalExceptionConverter duplicateConditionalExceptionConverter() {
        return new SQLDuplicateConditionalExceptionConverter();
    }

    @Bean
    @ConditionalOnMissingBean(IndexParameterNameResolver.class)
    public IndexParameterNameResolver parameterNameResolver() {
        return new IndexParameterNameResolverImpl();
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
    public void registerClassifiedResultCode(ClassifiedResultCodeRegistry registry) {
        registry.registerClassifiedResultCode("unique", getDuplicateResultCode());
    }

}
