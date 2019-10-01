package com.github.peacetrue.spring.expression;

import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author xiayx
 */
@Setter
public class MessageFormatterImpl implements MessageFormatter, BeanFactoryAware {

    private ExpressionParser expressionParser;
    private BeanResolver beanResolver;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        beanResolver = new BeanFactoryResolver(beanFactory);
    }

    @Override
    public String format(String message, Object payload) {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext(payload);
        evaluationContext.setBeanResolver(beanResolver);
        return expressionParser
                .parseExpression(message, MessageExpression.DEFAULT)
                .getValue(evaluationContext, String.class);
    }
}
