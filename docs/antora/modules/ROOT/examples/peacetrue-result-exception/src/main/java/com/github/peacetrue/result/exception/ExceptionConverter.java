package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;

/**
 * 异常转换器，用于转换一种特定的异常。
 *
 * @param <T> 异常类型
 * @author peace
 * @see ExceptionConvertService
 */
public interface ExceptionConverter<T extends Throwable> {

    /**
     * 将指定异常转换成响应结果
     *
     * @param throwable 系统中抛出的异常
     * @return 响应结果
     */
    Result convert(T throwable);

}
