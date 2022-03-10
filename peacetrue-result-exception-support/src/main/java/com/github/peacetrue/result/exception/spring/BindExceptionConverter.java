package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ExceptionConvertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 绑定异常转换器。
 *
 * @author peace
 */
@Slf4j
public class BindExceptionConverter extends AbstractExceptionConverter<BindException> {

    private ExceptionConvertService exceptionConvertService;

    @Override
    protected List<Result> resolveArgs(BindException throwable) {
        return throwable.getAllErrors().stream().map(this::convert).collect(Collectors.toList());
    }

    /**
     * 转换对象错误
     *
     * @param objectError 对象错误
     * @return 响应结果
     */
    private Result convert(ObjectError objectError) {
        if (objectError instanceof FieldError) {
            return convert((FieldError) objectError);
        } else {
            log.debug("转换'{}'对象错误为响应结果", objectError.getObjectName());
            ParameterConstraint<Class<?>, Object> parameter = new ParameterConstraint<>();
            parameter.setName(objectError.getObjectName());
            parameter.setConstraint(objectError.getArguments());
            return new DataResultImpl<>(objectError.getCode(), objectError.getDefaultMessage(), parameter);
        }
    }

    private Result convert(FieldError fieldError) {
        log.debug("转换'{}'字段错误为响应结果", fieldError.getField());
        Object source = fieldError.unwrap(Object.class);
        if (source instanceof Throwable) {
            return exceptionConvertService.convert((Throwable) source);
        }

        //TODO 有待进一步观察，什么时候不返回 codes
        return new DataResultImpl<>(
                Objects.requireNonNull(fieldError.getCodes())[1],
                getMessage(fieldError),
                new Parameter<>(fieldError.getField(), null, fieldError.getRejectedValue())
        );
    }

    private String getMessage(FieldError fieldError) {
        NamedMessage namedMessage = new NamedMessage(
                fieldError.getField(),
                Objects.requireNonNull(fieldError.getDefaultMessage())
        );
        return resultMessageBuilder.build("NamedMessage", namedMessage);
    }

    @Autowired
    public void setExceptionConvertService(ExceptionConvertService exceptionConvertService) {
        this.exceptionConvertService = exceptionConvertService;
    }

}
