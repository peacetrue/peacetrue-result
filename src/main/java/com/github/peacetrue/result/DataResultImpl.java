package com.github.peacetrue.result;

import lombok.Getter;
import lombok.Setter;

/**
 * 带负载数据的标准响应数据实现
 *
 * @author xiayx
 */
@Getter
@Setter
public class DataResultImpl<T> extends ResultImpl implements DataResult<T> {

    private static final long serialVersionUID = 0L;

    private T data;

    public DataResultImpl() { }

    public DataResultImpl(String code, String message) {
        super(code, message);
    }

    public DataResultImpl(String code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public DataResultImpl(Result result) {
        super(result);
    }

    public DataResultImpl(Result result, T data) {
        this(result);
        this.data = data;
    }

    public DataResultImpl(DataResult<T> result) {
        this(result, result.getData());
    }

    @Override
    public String toString() {
        return ResultUtils.toString(this);
    }
}
