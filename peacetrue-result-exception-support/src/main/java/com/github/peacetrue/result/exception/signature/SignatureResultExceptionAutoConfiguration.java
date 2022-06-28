package com.github.peacetrue.result.exception.signature;

import com.github.peacetrue.result.exception.ResultExceptionSupportAutoConfiguration;
import com.github.peacetrue.signature.SignatureInvalidException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 签名相关的异常响应结果自动配置。
 *
 * @author peace
 **/
@Configuration
@ConditionalOnClass(SignatureInvalidException.class)
@AutoConfigureAfter(ResultExceptionSupportAutoConfiguration.class)
public class SignatureResultExceptionAutoConfiguration {

    @Bean
    public ClientInvalidExceptionConverter clientInvalidExceptionConverter() {
        return new ClientInvalidExceptionConverter();
    }

    @Bean
    public TimestampInvalidExceptionConverter timestampInvalidExceptionConverter() {
        return new TimestampInvalidExceptionConverter();
    }

    @Bean
    public NonceInvalidExceptionConverter nonceInvalidExceptionConverter() {
        return new NonceInvalidExceptionConverter();
    }

    @Bean
    public SignatureInvalidExceptionConverter signatureInvalidExceptionConverter() {
        return new SignatureInvalidExceptionConverter();
    }

}
