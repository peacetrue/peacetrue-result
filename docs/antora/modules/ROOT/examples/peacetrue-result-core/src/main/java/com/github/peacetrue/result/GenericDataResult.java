package com.github.peacetrue.result;

import javax.annotation.Nullable;

/**
 * 通用的数据响应结果，主要是为了避免泛型参数警告。
 * <pre>
 *     //使用 DataResultImpl 会有泛型警告
 *     DataResult dataResult = this.restTemplate.getForObject("/path", DataResultImpl.class);
 *     //使用 GenericDataResult 不会有泛型警告
 *     GenericDataResult responseData = this.restTemplate.getForObject("/divide", GenericDataResult.class);
 * </pre>
 *
 * @author peace
 **/
public class GenericDataResult extends DataResultImpl<Object> {

    public GenericDataResult() {
    }

    public GenericDataResult(String code, @Nullable String message, @Nullable Object data) {
        super(code, message, data);
    }

    public GenericDataResult(Result result, @Nullable Object data) {
        super(result, data);
    }

    public GenericDataResult(DataResult<?> result) {
        this(result, result.getData());
    }
}
