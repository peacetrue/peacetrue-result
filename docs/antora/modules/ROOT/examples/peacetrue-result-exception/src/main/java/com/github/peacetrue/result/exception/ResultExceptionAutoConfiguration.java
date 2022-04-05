package com.github.peacetrue.result.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractResourceBasedMessageSource;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 响应结果异常自动配置。
 *
 * @author peace
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ResultExceptionProperties.class)
public class ResultExceptionAutoConfiguration {

    public static final String EXCEPTION_RESULT_CONTROLLER_ADVICE = "exceptionResultControllerAdvice";
    private final ResultExceptionProperties properties;

    public ResultExceptionAutoConfiguration(ResultExceptionProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(name = EXCEPTION_RESULT_CONTROLLER_ADVICE)
    @ConditionalOnProperty(value = "peacetrue.result.exception.disable-controller-exception-handler", havingValue = "false", matchIfMissing = true)
    public ExceptionResultControllerAdvice exceptionResultControllerAdvice() {
        log.debug("the ExceptionResultControllerAdvice is instance by @Bean rather than @ComponentScan");
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
        convertService.setIncludeMessageTemplateArgs(properties.getIncludeMessageTemplateArgs());
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
     * 依赖的 {@link ClassifiedResultCode} 示例化较晚，必须单独隔离配置，否则造成循环依赖。
     *
     * @author peace
     */
    @Configuration
    public static class ClassifiedResultCodeConfiguration {
        @Autowired(required = false)
        public void registerClassifiedResultCodes(@Nullable ClassifiedResultCodeRegistry registry,
                                                  @Nullable List<ClassifiedResultCode> codes) {
            if (registry == null || codes == null) return;
            codes.forEach(item -> registry.registerClassifiedResultCode(item.getSupperCode(), item.getCode()));
        }
    }

}
