package com.github.peacetrue.result.exception;

import java.util.*;

/**
 * 配置的响应结果编码分类器。
 *
 * @author peace
 **/
public class ConfiguredResultCodeClassifier implements ResultCodeClassifier {

    private final Map<String, Set<String>> classifiedCodes;

    public ConfiguredResultCodeClassifier() {
        this(Collections.emptyMap());
    }

    public ConfiguredResultCodeClassifier(Map<String, Set<String>> classifiedCodes) {
        this.classifiedCodes = new HashMap<>(Objects.requireNonNull(classifiedCodes));
    }

    @Override
    public String classifyResultCode(String code) {
        String[] parts = code.split("\\.", 2);
        return classifiedCodes.entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(item -> item.equals(parts[0])))
                .map(entry -> code.replaceFirst("^[^.]*", entry.getKey()))
                .findAny().orElse(code);
    }
}
