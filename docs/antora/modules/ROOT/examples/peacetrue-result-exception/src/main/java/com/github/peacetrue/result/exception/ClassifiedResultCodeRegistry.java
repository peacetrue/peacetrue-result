package com.github.peacetrue.result.exception;

/**
 * 分类的响应结果注册处。
 *
 * @author peace
 **/
public interface ClassifiedResultCodeRegistry {

    /**
     * 注册分类的响应结果编码
     *
     * @param superCode 父类编码
     * @param codes     当前编码
     */
    void registerClassifiedResultCode(String superCode, String... codes);

}
