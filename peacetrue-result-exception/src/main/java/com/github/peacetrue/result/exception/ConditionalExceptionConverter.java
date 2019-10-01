package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;

/**
 * the conditional exception converter
 *
 * @author xiayx
 * @see ExceptionConverter
 * @see ExceptionConvertService
 */
public interface ConditionalExceptionConverter {

    /**
     * determine if this exception is supported
     *
     * @param exception the exception
     * @return true if supports, otherwise false
     */
    boolean supports(Throwable exception);

    /**
     * convert {@link Exception} to {@link Result}
     *
     * @param exception the exception need to converted
     * @return the result converted by {@code exception}
     */
    Result convert(Throwable exception);

}
