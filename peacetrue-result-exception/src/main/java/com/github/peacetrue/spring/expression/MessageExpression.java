package com.github.peacetrue.spring.expression;

import org.springframework.expression.ParserContext;

/**
 * @author xiayx
 * @see ParserContext#TEMPLATE_EXPRESSION
 */
public class MessageExpression implements ParserContext {

    public static final MessageExpression DEFAULT = new MessageExpression();

    private MessageExpression() {
    }

    @Override
    public boolean isTemplate() {
        return true;
    }

    @Override
    public String getExpressionPrefix() {
        return "{";
    }

    @Override
    public String getExpressionSuffix() {
        return "}";
    }
}
