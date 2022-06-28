package com.github.peacetrue.result.exception;

import java.util.Optional;

/**
 * 响应结果编码分类器。分类响应结果编码，将过于具体的编码分类在通用编码下。
 *
 * @author peace
 **/
public interface ResultCodeClassifier {

    /** 默认的分类器，防止值为 {@code null} 时，占位使用 */
    ResultCodeClassifier DEFAULT = code -> Optional.empty();

    /**
     * 分类响应结果编码。
     *
     * @param code 响应结果编码子类
     * @return 响应结果编码父类
     */
    Optional<String> classifyResultCode(String code);

}
