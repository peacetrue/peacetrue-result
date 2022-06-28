package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.core.Ordered;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 响应结果异常自动配置。
 *
 * @author peace
 */
@Slf4j
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 3)
@AutoConfigureAfter(ResultBuilderAutoConfiguration.class)
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
        return new ExceptionConvertServiceImpl();
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
    public ResultExceptionThrowService resultExceptionThrowService() {
        return new ResultExceptionThrowServiceImpl();
    }

    @Bean
    public ConfiguredResultCodeClassifier configuredResultCodeClassifier() {
        return new ConfiguredResultCodeClassifier(properties.getClassifiedCodes());
    }

    /**
     * Spring Boot 1 和 Spring Boot 2 中，{@link ErrorAttributes} 所在包不同，需要区别处理。
     * {@link ResultErrorAttributes} 只支持 Spring Boot 2，不支持 Spring Boot 1。
     */
    @Configuration
    @ConditionalOnClass(name = "org.springframework.boot.web.servlet.error.ErrorAttributes")
    @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 3)
    @AutoConfigureBefore(name = "org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration")
    public static class ErrorAttributesConfiguration {
        @Bean
        public ResultErrorAttributes resultErrorAttributes() {
            return new ResultErrorAttributes();
        }
    }

    @Autowired
    public void registerMessageSourceBasename(AbstractResourceBasedMessageSource messageSource) {
        messageSource.addBasenames("peacetrue-result-exception");
    }

    /**
     * 避免循环依赖：
     * <p>
     * 首先初始化 ExceptionConvertServiceImpl（不依赖 ConditionalExceptionConverter），
     * 然后初始化 NestConditionalExceptionConverter（依赖 ExceptionConvertService），
     * 最后将 NestConditionalExceptionConverter 设置到 ExceptionConvertServiceImpl 中。
     */
    @Configuration
    @AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
    public static class ExceptionConvertServiceImplConfiguration {

        @Autowired
        @SuppressWarnings("rawtypes")
        public void registerExceptionConverter(ExceptionConvertService exceptionConvertService,
                                               List<ExceptionConverter> exceptionConverters) {
            if (exceptionConvertService instanceof ExceptionConvertServiceImpl) {
                ((ExceptionConvertServiceImpl) exceptionConvertService).setExceptionConverters(exceptionConverters);
            }
        }

        @Autowired
        public void registerConditionalExceptionConverter(ExceptionConvertService exceptionConvertService,
                                                          List<ConditionalExceptionConverter> conditionalExceptionConverters) {
            if (exceptionConvertService instanceof ExceptionConvertServiceImpl) {
                ((ExceptionConvertServiceImpl) exceptionConvertService).setConditionalExceptionConverters(conditionalExceptionConverters);
            }
        }
    }

    /**
     * 依赖的 {@link ClassifiedResultCode} 示例化较晚，必须单独隔离配置，否则造成循环依赖。
     *
     * @author peace
     */
    @Configuration
    @AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
    public static class ClassifiedResultCodeConfiguration {
        @Autowired(required = false)
        public void registerClassifiedResultCodes(@Nullable ClassifiedResultCodeRegistry registry,
                                                  @Nullable List<ClassifiedResultCode> codes) {
            if (registry == null || codes == null) return;
            codes.forEach(item -> registry.registerClassifiedResultCode(item.getSupperCode(), item.getCode()));
        }
    }

}
