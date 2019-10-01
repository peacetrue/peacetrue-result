package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;

/**
 * the service for exception convert
 *
 * @author xiayx
 */
public interface ExceptionConvertService {

    /**
     * convert {@link Exception} to {@link Result}
     *
     * @param exception the exception need to converted
     * @return the result converted by {@code exception}
     */
    Result convert(Throwable exception);
}
