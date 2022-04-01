package com.github.peacetrue.result;

import lombok.Getter;

import javax.annotation.Nullable;

/**
 * 响应结果异常。
 *
 * @author peace
 */
@Getter
public class ResultException extends RuntimeException implements Result {

    private final String code;

    public ResultException(String code, @Nullable String message) {
        super(message);
        this.code = code;
    }

    public ResultException(Result result) {
        this(result.getCode(), result.getMessage());
    }

    @Override
    public String toString() {
        return ResultUtils.toString(this);
    }

}
