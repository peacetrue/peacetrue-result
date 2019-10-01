package com.github.peacetrue.result;

import com.github.peacetrue.core.DataCapable;

/**
 * 带负载数据的标准响应数据
 *
 * @author xiayx
 * @see Result
 */
public interface DataResult<T> extends Result, DataCapable<T> {

    /** 具体负载数据 */
    T getData();
}
