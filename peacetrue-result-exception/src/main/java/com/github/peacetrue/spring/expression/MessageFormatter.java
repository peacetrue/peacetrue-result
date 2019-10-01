package com.github.peacetrue.spring.expression;

/**
 * 消息格式化器
 *
 * @author xiayx
 * @see org.springframework.context.MessageSource
 * @see java.text.MessageFormat
 */
public interface MessageFormatter {

    /** 格式化消息 */
    String format(String message, Object payload);

}
