package com.github.peacetrue.result.exception;

import com.github.peacetrue.core.CodeAware;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 标准响应数据异常
 *
 * @author xiayx
 */
@Setter
@Getter
public class ResultException extends RuntimeException implements Result, CodeAware {

    private static final long serialVersionUID = 0L;


    private String code;

    public ResultException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ResultException(Result result) {
        this(result.getCode(), result.getMessage());
    }

    public String getMessage() {
        return super.getMessage();
    }

    public String toString() {
        return ResultUtils.toString(this);
    }
}
