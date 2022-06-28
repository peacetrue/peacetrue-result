package com.github.peacetrue.result.builder.printer;

import org.springframework.format.Printer;

import java.util.Locale;

/**
 * 类打印器，打印类的描述信息
 *
 * @author peace
 */
public interface ClassPrinter extends Printer<Class<?>> {

    /**
     * 打印类的描述信息
     *
     * @param clazz  类
     * @param locale 方言
     * @return 描述信息
     */
    String print(Class<?> clazz, Locale locale);

}
