package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.exception.ExceptionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * 方法参数无效异常转换器。
 *
 * @author peace
 */
public class MethodArgumentNotValidExceptionConverter
        implements ExceptionConverter<MethodArgumentNotValidException> {

    private BindExceptionConverter bindExceptionConverter;

    public Result convert(MethodArgumentNotValidException exception) {
        return bindExceptionConverter.convert(new BindException(exception.getBindingResult()));
    }

    @Autowired
    public void setBindExceptionConverter(BindExceptionConverter bindExceptionConverter) {
        this.bindExceptionConverter = bindExceptionConverter;
    }
}
