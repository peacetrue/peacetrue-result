package com.github.peacetrue.result;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Collections;

/**
 * @author peace
 **/
public class ExpressionParserTest {

    @Test
    void map() {
        String test = "test";
        ExpressionParser expressionParser = new SpelExpressionParser();
        String value = expressionParser
                .parseExpression("#{#root[name]}", ParserContext.TEMPLATE_EXPRESSION)
                .getValue(new StandardEvaluationContext(Collections.singletonMap("name", test)), String.class);
        Assertions.assertEquals(test, value);
    }
}
