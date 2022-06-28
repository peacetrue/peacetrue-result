package com.github.peacetrue.result.exception.signature;

import com.github.peacetrue.lang.UncheckedException;
import org.springframework.util.ReflectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @author peace
 **/
class BeanUtils {

    private BeanUtils() {
    }

    private final static Map<Class<?>, BeanInfo> CACHE = new ConcurrentHashMap<>();

    static Map<String, Object> toBeanMap(Throwable throwable) {
        BeanInfo beanInfo = CACHE.computeIfAbsent(throwable.getClass(), BeanUtils::getBeanInfo);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        return Stream.of(propertyDescriptors).collect(
                HashMap::new,
                (m, v) -> m.put(v.getName(), ReflectionUtils.invokeMethod(v.getReadMethod(), throwable)),
                HashMap::putAll
        );
    }

    static BeanInfo getBeanInfo(Class<?> beanClass) {
        try {
            return Introspector.getBeanInfo(beanClass, Throwable.class, Introspector.IGNORE_ALL_BEANINFO);
        } catch (IntrospectionException e) {
            throw new UncheckedException(e);
        }
    }
}
