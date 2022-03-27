package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.*;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ClassifiedResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 绑定异常转换器。
 *
 * @author peace
 */
@Slf4j
public class BindExceptionConverter extends AbstractExceptionConverter<BindException> implements ClassifiedResultCode {

    /** @see org.springframework.beans.TypeMismatchException */
    private static final String TYPE_MISMATCH = "TypeMismatch";

    @Override
    protected List<Result> resolveArgs(BindException throwable) {
        return resolveArgs((BindingResult) throwable);
    }

    protected List<Result> resolveArgs(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream().map(this::convert).collect(Collectors.toList());
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
            log.debug("convert ObjectError '{}' to Result", objectError.getObjectName());
            //对象名称返回给调用者无意义，不是一个接口要求的参数。
//            ParameterConstraint<Class<?>, Object> parameter = new ParameterConstraint<>();
//            parameter.setName(objectError.getObjectName());
//            parameter.setConstraint(objectError.getArguments());
            return new ResultImpl(objectError.getCode(), objectError.getDefaultMessage());
        }
    }

    private Result convert(FieldError fieldError) {
        log.debug("convert FieldError '{}' to Result", fieldError.getField());
        Parameter<Class<?>, Object> parameter = new Parameter<>();
        parameter.setName(fieldError.getField());
        parameter.setValue(fieldError.getRejectedValue());
        if (TYPE_MISMATCH.equalsIgnoreCase(fieldError.getCode())) {
            parameter.setType(resolveClass(fieldError.getCodes()));
            String message = resultMessageBuilder.build(TYPE_MISMATCH, parameter);
            return new DataResultImpl<>(TYPE_MISMATCH + "." + parameter.getName(), message, parameter);
        }
        //TODO 有待进一步观察，什么时候不返回 codes
        return new DataResultImpl<>(
                Objects.requireNonNull(fieldError.getCodes())[1],
                getNamedMessage(fieldError),
                parameter
        );
    }

    @Nullable
    private Class<?> resolveClass(String[] codes) {
        try {
            String typeCode = codes[codes.length - 2];
            String type = typeCode.split("\\.", 2)[1];
            log.debug("got type '{}' from codes '{}'", type, Arrays.toString(codes));
            return Class.forName(type);
        } catch (Exception e) {
            log.warn("can't resolve class from '{}'", Arrays.toString(codes));
            return null;
        }
    }

    private String getNamedMessage(ObjectError error) {
        return getNamedMessage(error.getObjectName(), error.getDefaultMessage());
    }

    private String getNamedMessage(FieldError error) {
        return getNamedMessage(error.getField(), error.getDefaultMessage());
    }

    private String getNamedMessage(String name, String message) {
        return resultMessageBuilder.build("NamedMessage", new NamedMessage(
                name, Objects.requireNonNull(message)
        ));
    }

    @Override
    public String getSupperCode() {
        return ResultTypes.ERRORS.getCode();
    }
}
