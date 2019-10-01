package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;

/**
 * used to convert {@link Throwable} to {@link Result}
 *
 * @author xiayx
 * @see ConditionalExceptionConverter
 * @see ExceptionConvertService
 */
public interface ExceptionConverter<T extends Throwable> {

    /**
     * convert {@link Exception} to {@link Result}
     *
     * @param exception the exception need to converted
     * @return the result converted by {@code exception}
     */
    Result convert(T exception);


}
