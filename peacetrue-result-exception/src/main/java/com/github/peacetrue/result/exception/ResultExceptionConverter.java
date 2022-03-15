package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultException;
import com.github.peacetrue.result.ResultUtils;

/**
 * 转换成非异常的响应结果。
 *
 * @author peace
 */
public class ResultExceptionConverter implements ExceptionConverter<ResultException> {

    @Override
    public Result convert(ResultException exception) {
        return ResultUtils.build(exception);
    }
}
