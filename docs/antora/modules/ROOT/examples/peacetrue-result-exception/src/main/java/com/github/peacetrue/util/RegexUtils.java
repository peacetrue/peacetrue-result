package com.github.peacetrue.util;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类。
 *
 * @author peace
 */
public abstract class RegexUtils {

    /** 抽象工具类，防止实例化 */
    protected RegexUtils() {
    }

    /**
     * 从字符串中提取符合正则表达式规则的变量值。
     *
     * @param pattern 正则表达式
     * @param string  字符串
     * @return 正则表达式中占位符匹配的值
     */
    public static String[] extractValues(Pattern pattern, String string) {
        Matcher matcher = pattern.matcher(string);
        if (!matcher.find()) return new String[0];
        String[] placeholders = new String[matcher.groupCount()];
        for (int i = 1; i <= matcher.groupCount(); i++) {
            placeholders[i - 1] = matcher.group(i);
        }
        return placeholders;
    }

    /**
     * 替换字符串中正则表达式匹配的值。
     *
     * @param pattern  正则表达式
     * @param string   字符串
     * @param replacer 替换匹配的值
     * @return 替换后的字符串
     */
    public static String replaceAll(Pattern pattern, String string, Function<Matcher, String> replacer) {
        StringBuffer replacedString = new StringBuffer();
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            matcher.appendReplacement(replacedString, replacer.apply(matcher));
        }
        matcher.appendTail(replacedString);
        return replacedString.toString();
    }

}
