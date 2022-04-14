package com.github.peacetrue.result.builder;

import com.github.peacetrue.result.ResultCustomizer;
import com.github.peacetrue.result.builder.printer.ClassPrinter;
import com.github.peacetrue.result.builder.printer.MessageSourceClassPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * 响应结果构建者自动配置
 *
 * @author peace
 */
@Configuration
@EnableConfigurationProperties(ResultBuilderProperties.class)
public class ResultBuilderAutoConfiguration {

    public static final String CONVERSION_SERVICE_NAME = "resultBuilderConversionService";

    private final ResultBuilderProperties properties;

    public ResultBuilderAutoConfiguration(ResultBuilderProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(ResultMessageBuilder.class)
    public ResultMessageBuilder resultMessageBuilder() {
        return new MessageSourceResultMessageBuilder(
                properties.getCodePrefix(), properties.getDefaultMessage()
        );
    }

    @Bean
    @ConditionalOnMissingBean(ExpressionParser.class)
    public ExpressionParser expressionParser() {
        return new SpelExpressionParser();
    }

    @Bean
    @ConditionalOnMissingBean(ClassPrinter.class)
    public ClassPrinter classPrinter(MessageSource messageSource) {
        return new MessageSourceClassPrinter(properties.getClassPrefix(), messageSource);
    }

    @Bean
    @Autowired(required = false)
    @ConditionalOnMissingBean(name = CONVERSION_SERVICE_NAME)
    public FormattingConversionService resultBuilderConversionService(@Nullable List<ConversionService> conversionServices) {
        if (conversionServices == null) conversionServices = Collections.emptyList();
        return conversionServices.stream()
                .filter(FormattingConversionService.class::isInstance)
                .findFirst().map(FormattingConversionService.class::cast)
                .orElseGet(DefaultFormattingConversionService::new)
                ;
    }

    @Configuration
    public static class ClassPrinterRegisterConfiguration {
        @Autowired
        public void registerFormatter(@Qualifier(CONVERSION_SERVICE_NAME) FormatterRegistry registry,
                                      ClassPrinter classPrinter) {
            registry.addFormatterForFieldType(Class.class, classPrinter, null);
        }
    }

    @Bean
    @ConditionalOnMissingBean(ResultCustomizer.class)
    public ResultCustomizer resultCustomizer() {
        return CollectionUtils.isEmpty(properties.getCustomCodes())
                ? ResultCustomizer.DEFAULT
                : new ResultCodeCustomizer(properties.getCustomCodes());
    }


    @Autowired
    public void registerMessageSourceBasename(AbstractResourceBasedMessageSource messageSource) {
        messageSource.addBasenames("peacetrue-result-builder");
    }

}
