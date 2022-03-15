package com.github.peacetrue.result.dubbo;

import com.alibaba.dubbo.common.log.Logger;
import com.alibaba.dubbo.rpc.Result;
import com.github.peacetrue.result.exception.ResultException;

/**
 * to support return {@link ResultException} directly.
 *
 * @author peace
 */
public class ResultExceptionFilter extends ExceptionFilter {

    public static final String CONFIG_NAME = "ResultExceptionFilter,-exception";

    public ResultExceptionFilter() {
    }

    public ResultExceptionFilter(Logger log) {
        super(log);
    }

    @Override
    protected Result handleResult(Result result) {
        if (result.getException() instanceof ResultException) {
            return result;
        }
        return super.handleResult(result);
    }
}
