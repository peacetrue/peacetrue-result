package com.github.peacetrue.result.exception;

import com.github.peacetrue.core.DataAware;
import com.github.peacetrue.result.DataResult;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 带负载数据的标准响应数据异常
 *
 * @author xiayx
 */
@Getter
@Setter
public class DataResultException extends ResultException implements DataResult<Object>, DataAware<Object> {

    private static final long serialVersionUID = 0L;

    private Object data;

    public DataResultException(String code, String message, Object data) {
        super(code, message);
        this.data = data;
    }

    public DataResultException(Result result, Object data) {
        super(result);
        this.data = data;
    }

    public DataResultException(DataResult<?> result) {
        this(result, result.getData());
    }

    public String toString() {
        return ResultUtils.toString(this);
    }
}
