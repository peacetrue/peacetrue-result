package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ClassifiedResultCode;
import com.github.peacetrue.result.exception.ExceptionConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;

import javax.annotation.Nullable;

/**
 * {@link HttpMessageNotReadableException} 转换器。
 *
 * @author peace
 * @see com.github.peacetrue.result.exception.NestConditionalExceptionConverter
 **/
public class HttpMessageNotReadableExceptionConverter
        extends AbstractExceptionConverter<HttpMessageNotReadableException>
        implements ClassifiedResultCode {

    private ExceptionConvertService exceptionConvertService;

    @Override
    public Result convert(HttpMessageNotReadableException exception) {
        if (exception.getCause() == null) return super.convert(exception);
        return exceptionConvertService.convert(exception.getCause());
    }

    @Nullable
    @Override
    protected Object resolveArgs(HttpMessageNotReadableException exception) {
        //Required request body is missing: ........
        return null;
    }

    @Override
    public String getSupperCode() {
        return ResultTypes.PARAMETER_MISSING.getCode();
    }

    @Autowired
    public void setExceptionConvertService(ExceptionConvertService exceptionConvertService) {
        this.exceptionConvertService = exceptionConvertService;
    }
}
