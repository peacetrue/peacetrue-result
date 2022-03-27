package com.github.peacetrue.result.exception;

/**
 * 分类的响应结果编码。
 *
 * @author peace
 **/
public interface ClassifiedResultCode {

    /**
     * 获取当前响应结果编码
     *
     * @return 当前响应结果编码
     */
    String getCode();

    /**
     * 获取父类响应结果编码
     *
     * @return 父类响应结果编码
     */
    String getSupperCode();
}
