package com.github.peacetrue.result.exception.jackson;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ClassifiedResultCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 使用 {@link RequestBody} 接收 JSON 参数时，参数类型错误，会抛出 {@link HttpMessageNotReadableException}。
 * {@link HttpMessageNotReadableException#getCause()} 为 {@link InvalidFormatException}。
 *
 * @author peace
 * @see com.github.peacetrue.result.exception.spring.HttpMessageNotReadableExceptionConverter
 **/
public class InvalidFormatExceptionConverter
        extends AbstractExceptionConverter<InvalidFormatException>
        implements ClassifiedResultCode {

    private static String getName(List<JsonMappingException.Reference> paths) {
        return paths.stream()
                .map(InvalidFormatExceptionConverter::getFieldName)
                .collect(Collectors.joining("."));
    }

    private static String getFieldName(JsonMappingException.Reference reference) {
        return reference.getIndex() == -1
                ? reference.getFieldName()
                : (Objects.toString(reference.getFieldName(), "") + "[" + reference.getIndex() + "]");
    }

    @Override
    protected Object resolveArgs(InvalidFormatException exception) {
        Parameter<Class<?>, Object> parameter = new Parameter<>();
        parameter.setName(getName(exception.getPath()));
        parameter.setType(exception.getTargetType());
        parameter.setValue(exception.getValue());
        return parameter;
    }

    @Override
    public String getSupperCode() {
        return ResultTypes.PARAMETER_INVALID.getCode();
    }


}
