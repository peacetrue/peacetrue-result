package com.github.peacetrue.result.exception.web;

import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultType;
import com.github.peacetrue.result.exception.ExceptionConverter;
import com.github.peacetrue.result.payload.Parameter;
import com.github.peacetrue.spring.expression.MessageFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * support {@link BindException}
 *
 * @author xiayx
 */
public class BindExceptionConverter implements ExceptionConverter<BindException> {

    private static Logger logger = LoggerFactory.getLogger(BindExceptionConverter.class);

    @Autowired
    private MessageFormatter messageFormatter;

    public Result convert(BindException exception) {
        return convert(exception.getBindingResult());
    }

    /**
     * convert BindingResult to Result
     *
     * @param bindingResult the bindingResult
     * @return the Result
     */
    protected Result convert(BindingResult bindingResult) {
        List<ObjectError> errors = bindingResult.getAllErrors();
        List<Result> results = errors.stream().map(this::map).collect(Collectors.toList());
        return new DataResultImpl<>(ResultType.errors.getCode(),
                messageFormatter.format(ResultType.errors.getMessage(), results),
                results);
    }

    /**
     * convert objectError to Result
     *
     * @param objectError the objectError
     * @return the Result
     */
    public Result map(ObjectError objectError) {
        logger.debug("转换[{}]为标准响应数据", objectError);
        Parameter<Class, Object> parameter = new Parameter<>();
        if (objectError instanceof FieldError) {
            FieldError fieldError = (FieldError) objectError;
            parameter.setName(fieldError.getField());
            parameter.setValue(fieldError.getRejectedValue());
            if ("typeMismatch".equals(fieldError.getCode())) {
                return new DataResultImpl<>(
                        ResultType.parameter_format_error.getCode(),
                        messageFormatter.format(ResultType.parameter_format_error.getMessage(), parameter),
                        parameter);
            }
            return new DataResultImpl<>(fieldError.getCode(), fieldError.getDefaultMessage(), parameter);
        } else {
            //no data available
            parameter.setName(objectError.getObjectName());
            return new DataResultImpl<>(objectError.getCode(), objectError.getDefaultMessage(), parameter);
        }
    }

//    public static Result map(ConstraintViolation violation) {
//        Parameter<Class,Object> parameter = new Parameter<>();
//        parameter.setName(violation.getPropertyPath().toString());
//        return new DataResultImpl<>(violation.get, message, parameter);
//    }

}
