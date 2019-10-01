package com.github.peacetrue.result.success;

import com.github.peacetrue.configuration.ConfigurationUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.*;

/**
 * 可通过配置排除指定方法
 *
 * @author xiayx
 */
@ControllerAdvice
public class ExcludeMethodResultAutoSuccess extends ResultAutoSuccess {

    private Map<Class, Set<String>> disabledMethods;

    public ExcludeMethodResultAutoSuccess() {
        this.disabledMethods = Collections.emptyMap();
    }

    public ExcludeMethodResultAutoSuccess(Map<Class, Set<String>> disabledMethods) {
        this.disabledMethods = new HashMap<>(Objects.requireNonNull(disabledMethods));
    }

    @Override
    protected boolean exclude(MethodParameter returnType) {
        return ConfigurationUtils.contains(disabledMethods, returnType.getMethod());
    }
}
