package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;

/**
 * 条件性异常转换器。
 *
 * @author peace
 * @see ExceptionConverter
 * @see ExceptionConvertService
 */
public interface ConditionalExceptionConverter {

    /**
     * 是否支持此异常。
     *
     * @param exception 异常
     * @return true 如果支持
     */
    boolean supports(Throwable exception);

    /**
     * 转换异常为数据响应结果。
     *
     * @param exception 异常
     * @return 响应结果
     * @see ExceptionConverter
     */
    Result convert(Throwable exception);

}
