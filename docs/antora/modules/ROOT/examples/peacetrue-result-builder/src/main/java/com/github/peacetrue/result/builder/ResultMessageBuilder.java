package com.github.peacetrue.result.builder;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * 响应结果描述构建者，用于支持国际化响应结果描述。
 *
 * @author peace
 */
public interface ResultMessageBuilder {

    /**
     * 构建响应结果描述
     *
     * @param code 响应结果编码
     * @return 响应结果描述
     */
    default String build(String code) {
        return build(code, (Object) null);
    }

    /**
     * 构建响应结果描述
     *
     * @param code   响应结果编码
     * @param locale 方言
     * @return 响应结果描述
     */
    default String build(String code, Locale locale) {
        return build(code, null, locale);
    }

    /**
     * 构建响应结果描述
     *
     * @param code 响应结果编码
     * @param args 上下文参数
     * @return 响应结果描述
     */
    default String build(String code, @Nullable Object args) {
        return build(code, args, Locale.getDefault());
    }


    /**
     * 构建响应结果描述
     *
     * @param code   响应结果编码
     * @param args   上下文参数
     * @param locale 方言
     * @return 响应结果描述
     */
    String build(String code, @Nullable Object args, Locale locale);

}
