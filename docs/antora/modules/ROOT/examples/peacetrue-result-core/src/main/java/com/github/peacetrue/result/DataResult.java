package com.github.peacetrue.result;

import com.github.peacetrue.beans.properties.data.DataCapable;

import javax.annotation.Nullable;

/**
 * 数据响应结果，带负载数据的响应结果。
 *
 * @author peace
 * @see Result
 */
public interface DataResult<T> extends Result, DataCapable<T> {

    /** 具体的负载数据，比如查询时返回的列表数据 */
    @Nullable
    T getData();

}
