package com.github.peacetrue.result.builder;

import com.github.peacetrue.result.ResultCustomizer;
import com.github.peacetrue.result.builder.printer.ClassPrinter;
import com.github.peacetrue.result.builder.printer.MessageSourceClassPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.core.Ordered;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.CollectionUtils;

/**
 * 响应结果构建者自动配置
 *
 * @author peace
 */
@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 1)
@AutoConfigureAfter(ResultMessageSourceAutoConfiguration.class)
@EnableConfigurationProperties(ResultBuilderProperties.class)
public class ResultBuilderAutoConfiguration {

    public static final String RESULT_BUILDER_CONVERSION_SERVICE = "resultBuilderConversionService";
    public static final String RESULT_BUILDER_EXPRESSION_PARSER = "resultBuilderExpressionParser";

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
    @ConditionalOnMissingBean(name = RESULT_BUILDER_EXPRESSION_PARSER)
    public ExpressionParser resultBuilderExpressionParser() {
        return new SpelExpressionParser();
    }

    @Bean
    @ConditionalOnMissingBean(name = RESULT_BUILDER_CONVERSION_SERVICE)
    public FormattingConversionService resultBuilderConversionService(@Autowired ClassPrinter classPrinter) {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        conversionService.addFormatterForFieldType(Class.class, classPrinter, (text, locale) -> Object.class);
        return conversionService;
    }

    @Bean
    @ConditionalOnMissingBean(ClassPrinter.class)
    public ClassPrinter classPrinter(MessageSource messageSource) {
        return new MessageSourceClassPrinter(properties.getClassPrefix(), messageSource);
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
