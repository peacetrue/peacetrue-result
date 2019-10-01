package com.github.peacetrue.configuration;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * 配置工具类
 *
 * @author xiayx
 */
public abstract class ConfigurationUtils {

    /** 配置{@code methods}中是否包含指定方法{@code method} */
    public static boolean contains(Map<Class, Set<String>> methods, Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        Set methodNames = methods.get(declaringClass);
        return methodNames != null && methodNames.contains(method.getName());
    }
}
