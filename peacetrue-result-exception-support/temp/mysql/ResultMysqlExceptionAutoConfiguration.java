package com.github.peacetrue.result.exception.mysql;

import com.github.peacetrue.result.exception.ExceptionResultAutoConfiguration;
import com.github.peacetrue.result.exception.ExceptionResultProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * the AutoConfiguration for mysql exception convert
 *
 * @author xiayx
 */
@ConditionalOnClass(com.mysql.jdbc.Driver.class)
@AutoConfigureAfter(ExceptionResultAutoConfiguration.class)
@EnableConfigurationProperties(ResultMysqlExceptionProperties.class)
public class ResultMysqlExceptionAutoConfiguration {

    private ResultMysqlExceptionProperties properties;

    public ResultMysqlExceptionAutoConfiguration(ResultMysqlExceptionProperties properties) {
        this.properties = properties;
    }

    @Bean
    public MysqlExceptionConverter mysqlExceptionConverter() {
        MysqlExceptionConverter converter = new MysqlExceptionConverter();
        converter.setSqlStateMessagePatterns(properties.getSqlStateMessagePatterns());
        return converter;
    }

    @Autowired
    public void addProxyClasses(ExceptionResultProperties properties) {
        properties.addProxyClasses(
                "org.hibernate.exception.ConstraintViolationException",
                "javax.persistence.PersistenceException",
                "org.springframework.dao.DuplicateKeyException",
                "org.springframework.http.converter.HttpMessageNotReadableException"
        );
    }

}
