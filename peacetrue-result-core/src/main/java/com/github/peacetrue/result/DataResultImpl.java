package com.github.peacetrue.result;

import com.github.peacetrue.beans.properties.data.DataAware;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

/**
 * 数据响应结果的基本实现。
 *
 * @author peace
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DataResultImpl<T> extends ResultImpl implements DataResult<T>, DataAware<T> {

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
