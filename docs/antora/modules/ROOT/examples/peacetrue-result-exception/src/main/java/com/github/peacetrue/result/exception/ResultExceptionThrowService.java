package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.DataResultException;
import com.github.peacetrue.result.ResultException;

import java.util.Locale;

/**
 * 响应结果异常抛出服务，用于抛出响应结果异常。
 *
 * @author peace
 **/
public interface ResultExceptionThrowService {

    /**
     * 抛出响应结果异常
     *
     * @param code 响应结果编码
     * @throws ResultException 响应结果异常
     */
    default void throwResultException(String code) throws ResultException {
        throwResultException(code, Locale.getDefault());
    }

    /**
     * 抛出响应结果异常
     *
     * @param code   响应结果编码
     * @param locale 方言
     * @throws ResultException 响应结果异常
     */
    void throwResultException(String code, Locale locale) throws ResultException;

    /**
     * 抛出数据响应结果异常
     *
     * @param code 响应结果编码
     * @param data 响应结果数据
     * @throws DataResultException 数据响应结果异常
     */
    default void throwDataResultException(String code, Object data) throws DataResultException {
        throwDataResultException(code, data, Locale.getDefault());
    }

    /**
     * 抛出数据响应结果异常
     *
     * @param code   响应结果编码
     * @param data   响应结果数据
     * @param locale 方言
     * @throws DataResultException 数据响应结果异常
     */
    void throwDataResultException(String code, Object data, Locale locale) throws DataResultException;
}
