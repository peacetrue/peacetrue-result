package com.github.peacetrue.result.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractResourceBasedMessageSource;

import java.util.List;

/**
 * 响应结果异常自动配置。
 *
 * @author peace
 */
@Configuration
@EnableConfigurationProperties(ExceptionResultProperties.class)
public class ExceptionResultAutoConfiguration {

    public static final String EXCEPTION_RESULT_CONTROLLER_ADVICE = "exceptionResultControllerAdvice";
    private final ExceptionResultProperties properties;

    public ExceptionResultAutoConfiguration(ExceptionResultProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(name = EXCEPTION_RESULT_CONTROLLER_ADVICE)
    @ConditionalOnProperty(value = "peacetrue.result.exception.disable-controller-exception-handler", havingValue = "false", matchIfMissing = true)
    public ExceptionResultControllerAdvice exceptionResultControllerAdvice() {
        return new ExceptionResultControllerAdvice();
    }

    @Bean
    public ExceptionResultHandler exceptionResultHandler() {
        return new ExceptionResultHandler();
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionConvertService.class)
    public ExceptionConvertService exceptionConvertService() {
        ExceptionConvertServiceImpl convertService = new ExceptionConvertServiceImpl();
        convertService.setIncludeStackTrace(properties.getIncludeStackTrace());
        convertService.setRetainResultData(properties.getRetainResultData());
        return convertService;
    }

    @Bean
    public FallbackExceptionConverter fallbackExceptionConverter() {
        return new FallbackExceptionConverter();
    }

    @Bean
    public ResultExceptionConverter resultExceptionConverter() {
        return new ResultExceptionConverter();
    }

    @Bean
    public NestConditionalExceptionConverter nestConditionalExceptionConverter() {
        return new NestConditionalExceptionConverter(properties.getNestClasses());
    }

    @Bean
    @ConditionalOnMissingBean(ResultExceptionThrowService.class)
    public ResultExceptionThrowService exceptionThrowService() {
        return new ResultExceptionThrowServiceImpl();
    }

    @Bean
    public ConfiguredResultCodeClassifier configuredResultCodeClassifier() {
        return new ConfiguredResultCodeClassifier(properties.getClassifiedCodes());
    }

    @Bean
    public ResultErrorAttributes resultErrorAttributes() {
        return new ResultErrorAttributes();
    }

    @Autowired
    public void registerMessageSourceBasename(AbstractResourceBasedMessageSource messageSource) {
        messageSource.addBasenames("peacetrue-result-exception");
    }

    /**
     * 依赖的 {@link ClassifiedResultCode} 示例化在较晚的时机，必须单独隔离配置，否则造成循环依赖。
     *
     * @author peace
     */
    @Configuration
    public static class ClassifiedResultCodeConfiguration {
        @Autowired
        public void registerClassifiedResultCodes(ClassifiedResultCodeRegistry registry, List<ClassifiedResultCode> codes) {
            codes.forEach(item -> registry.registerClassifiedResultCode(item.getSupperCode(), item.getCode()));
        }
    }

}