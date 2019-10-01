package com.github.peacetrue.spring.expression;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author xiayx
 */
@Configuration
public class MessageExpressionAutoConfiguration {

    private static final String MESSAGE_EXPRESSION_PARSER = "messageExpressionParser";

    @Bean
    @ConditionalOnMissingBean(name = MESSAGE_EXPRESSION_PARSER)
    public ExpressionParser messageExpressionParser() {
        return new SpelExpressionParser(new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, null));
    }

    @Bean
    @ConditionalOnMissingBean(MessageFormatter.class)
    public MessageFormatter messageFormatter(@Qualifier(MESSAGE_EXPRESSION_PARSER) ExpressionParser expressionParser) {
        MessageFormatterImpl messageFormatter = new MessageFormatterImpl();
        messageFormatter.setExpressionParser(expressionParser);
        return messageFormatter;
    }
}
