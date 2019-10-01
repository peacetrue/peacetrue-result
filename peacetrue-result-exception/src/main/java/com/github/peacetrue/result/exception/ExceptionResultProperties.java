package com.github.peacetrue.result.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * the properties for Result Exception
 *
 * @author xiayx
 */
@ConfigurationProperties(prefix = "peacetrue.result.exception")
public class ExceptionResultProperties {

    private static Logger logger = LoggerFactory.getLogger(ExceptionResultProperties.class);

    /** the proxy exception, delegate to {@link Exception#getCause()} as implement */
    private List<Class<? extends Throwable>> proxyClasses = new ArrayList<>();
    /** 无参的异常类 */
    private Set<Class<? extends Throwable>> noArgsClasses = new HashSet<>();

    public List<Class<? extends Throwable>> getProxyClasses() {
        return proxyClasses;
    }

    public void setProxyClasses(List<Class<? extends Throwable>> proxyClasses) {
        this.proxyClasses = proxyClasses;
    }

    public Set<Class<? extends Throwable>> getNoArgsClasses() {
        return noArgsClasses;
    }

    public void setNoArgsClasses(Set<Class<? extends Throwable>> noArgsClasses) {
        this.noArgsClasses = noArgsClasses;
    }

    @SuppressWarnings("unchecked")
    public void addProxyClasses(String... proxyClasses) {
        this.proxyClasses.addAll(resolveClasses(proxyClasses, Throwable.class));
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<Class<? extends T>> resolveClasses(String[] stringClasses, Class<T> targetClass) {
        Set<Class<? extends T>> classes = new HashSet<>(stringClasses.length);
        for (String stringClass : stringClasses) {
            try {
                Class<?> aClass = Class.forName(stringClass);
                if (targetClass == null || targetClass.isAssignableFrom(aClass)) {
                    classes.add((Class<? extends T>) aClass);
                } else {
                    logger.warn("ignored class '{}' which is not subclass of '{}'", stringClass, targetClass);
                }
            } catch (ClassNotFoundException ignored) {
                logger.warn("ignored class '{}' which is unknown", stringClass);
            }
        }
        return classes;
    }
}
