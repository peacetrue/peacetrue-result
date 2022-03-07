package com.github.peacetrue.result;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

/**
 * 数据响应结果异常
 *
 * @author peace
 */
@Getter
@Setter
public class DataResultException extends ResultException implements DataResult<Object> {

    private final Object data;

    public DataResultException(String code, @Nullable String message, @Nullable Object data) {
        super(code, message);
        this.data = data;
    }

    @Override
    public String toString() {
        return ResultUtils.toString(this);
    }

}
