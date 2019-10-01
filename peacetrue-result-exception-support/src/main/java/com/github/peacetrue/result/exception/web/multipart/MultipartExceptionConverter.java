package com.github.peacetrue.result.exception.web.multipart;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultType;
import com.github.peacetrue.result.exception.ExceptionConvertService;
import com.github.peacetrue.result.exception.SimplifiedExceptionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartException;

public class MultipartExceptionConverter extends SimplifiedExceptionConverter<MultipartException> {

    @Autowired
    private ExceptionConvertService exceptionConvertService;

    @Override
    public Result convert(MultipartException exception) {
        if (exception.getMessage().startsWith("Current request is not a multipart request")) {
            return super.convert(exception);
        } else if (exception.getMessage().startsWith("Could not parse multipart servlet request")) {
            return exceptionConvertService.convert(exception.getCause());
        }
        return ResultType.failure;
    }
}