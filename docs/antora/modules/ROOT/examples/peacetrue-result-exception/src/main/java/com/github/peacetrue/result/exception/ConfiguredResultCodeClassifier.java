package com.github.peacetrue.result.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 配置的响应结果编码分类器。
 *
 * @author peace
 **/
@Slf4j
public class ConfiguredResultCodeClassifier implements ResultCodeClassifier, ClassifiedResultCodeRegistry {

    private final Map<String, Set<String>> classifiedCodes;

    public ConfiguredResultCodeClassifier() {
        this(new HashMap<>());
    }

    public ConfiguredResultCodeClassifier(Map<String, Set<String>> classifiedCodes) {
        this.classifiedCodes = new HashMap<>(Objects.requireNonNull(classifiedCodes));
    }

    @Override
    @SuppressWarnings("java:S3864")
    public Optional<String> classifyResultCode(String code) {
        String[] parts = code.split("\\.", 2);
        return classifiedCodes.entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(item -> item.equals(parts[0])))
                .map(entry -> code.replaceFirst("^[^.]*", entry.getKey()))
                .peek(superCode -> log.debug("classify Result.code: {} -> {}", code, superCode))
                .findAny();
    }

    @Override
    public void registerClassifiedResultCode(String superCode, String... codes) {
        log.debug("register classified Result.code: {} <- {}", superCode, codes);
        Set<String> existSubCodes = classifiedCodes.get(superCode);
        if (existSubCodes == null) {
            classifiedCodes.put(superCode, new HashSet<>(Arrays.asList(codes)));
        } else {
            existSubCodes.addAll(Arrays.asList(codes));
        }
    }
}
