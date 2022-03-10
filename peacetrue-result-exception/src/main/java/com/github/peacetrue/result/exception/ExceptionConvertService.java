package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;

/**
 * 异常转换器服务，用于转换系统中所有的异常。
 *
 * @author peace
 * @see ExceptionConverter
 */
public interface ExceptionConvertService {

    /**
     * 转换异常为响应结果
     *
     * @param throwable 系统中抛出的异常
     * @return 转换成的响应结果
     */
    Result convert(Throwable throwable);


}
