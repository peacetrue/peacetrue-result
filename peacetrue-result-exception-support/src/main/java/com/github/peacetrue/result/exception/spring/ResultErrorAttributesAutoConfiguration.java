package com.github.peacetrue.result.exception.spring;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;

/**
 * 该类无法实现在 Spring 装载 {@link DefaultErrorAttributes} 之前执行 {@link ResultErrorAttributesUtils#extendDefaultErrorAttributes}。
 */
@Deprecated
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnClass(ErrorAttributes.class)
public class ResultErrorAttributesAutoConfiguration {
    static {
        ResultErrorAttributesUtils.extendDefaultErrorAttributes();
    }
}
