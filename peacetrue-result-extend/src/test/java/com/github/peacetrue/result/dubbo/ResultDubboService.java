package com.github.peacetrue.result.dubbo;

import com.github.peacetrue.result.exception.ResultException;

/**
 * @author peace
 */
public interface ResultDubboService {
    default void throwException() {
        throw new ResultException("dubboException", "");
    }
}
