package com.github.peacetrue.result.exception;

import com.github.peacetrue.beans.properties.data.DataAware;
import com.github.peacetrue.result.DataResult;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultException;
import com.github.peacetrue.result.ResultUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 带负载数据的响应结果异常
 *
 * @author peace
 */
@Getter
@Setter
public class DataResultException extends ResultException implements DataResult<Object>, DataAware<Object> {

    private static final long serialVersionUID = 0L;

    /** 异常时的上下文信息 */
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
