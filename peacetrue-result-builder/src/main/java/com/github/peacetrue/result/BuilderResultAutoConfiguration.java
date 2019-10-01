package com.github.peacetrue.result;

import com.github.peacetrue.printer.ClassPrinter;
import com.github.peacetrue.printer.MessageSourceClassPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * used to build result
 *
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(BuilderResultProperties.class)
@AutoConfigureAfter(MessageSourceAutoConfiguration.class)
@PropertySource("classpath:peacetrue-result.properties")
public class BuilderResultAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private BuilderResultProperties properties;

    public BuilderResultAutoConfiguration(BuilderResultProperties properties) {
        this.properties = properties;
    }

    @Bean
    @Primary
    public ResultBuilder resultBuilder() {
        return new CodeConvertResultBuilder();
    }

    @Bean
    public ResultBuilder messageSourceResultBuilder(MessageSource messageSource,
                                                    ExpressionParser expressionParser) {
        MessageSourceResultBuilder builder = new MessageSourceResultBuilder();
        builder.setPrefix(properties.getCodePrefix());
        builder.setMessageSource(messageSource);
        builder.setExpressionParser(expressionParser);
        return builder;
    }

    @Bean
    @ConditionalOnMissingBean(ClassPrinter.class)
    public ClassPrinter classPrinter() {
        MessageSourceClassPrinter printer = new MessageSourceClassPrinter();
        printer.setPrefix(properties.getClassPrefix());
        return printer;
    }

    @Bean
    @ConditionalOnMissingBean(ResultCodeConverter.class)
    public ResultCodeConverter resultCodeResolver() {
        SimpleResultCodeResolver resolver = new SimpleResultCodeResolver();
        resolver.setCodes(properties.getCodes());
        return resolver;
    }

    @Bean
    public ExpressionParser expressionParser() {
        return new SpelExpressionParser();
    }

    @Autowired
    public void addResultMessage(MessageSource messageSource) {
        //add default result builder messages config
        if (messageSource instanceof ResourceBundleMessageSource) {
            ResourceBundleMessageSource bundleMessageSource = (ResourceBundleMessageSource) messageSource;
            if (!bundleMessageSource.getBasenameSet().contains("peacetrue-result-messages")) {
                bundleMessageSource.addBasenames("peacetrue-result-messages");
                logger.debug("add default config 'peacetrue-result-messages.properties' to '{}'", messageSource);
            }
        } else {
            logger.warn("the default config 'peacetrue-result-messages.properties' can't be added to '{}'", messageSource);
        }
    }


}
