package com.github.peacetrue.result.exception;

/**
 * 响应结果编码分类器
 *
 * @author peace
 **/
public interface ResultCodeClassifier {

    /** 默认的分类器，防止值为 {@code null} 时，占位使用 */
    ResultCodeClassifier DEFAULT = code -> code;

    /**
     * 分类响应结果编码
     *
     * @param code 响应结果编码子类
     * @return 响应结果编码父类
     */
    String classifyResultCode(String code);

}
