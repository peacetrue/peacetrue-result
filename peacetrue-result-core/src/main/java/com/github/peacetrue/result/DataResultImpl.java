package com.github.peacetrue.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;

/**
 * 数据响应结果的基本实现
 *
 * @author peace
 */
@Getter
@Setter
@NoArgsConstructor
public class DataResultImpl<T> extends ResultImpl implements DataResult<T> {

    private T data;

    public DataResultImpl(String code, @Nullable String message, @Nullable T data) {
        super(code, message);
        this.data = data;
    }

    public DataResultImpl(Result result, @Nullable T data) {
        super(result);
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
