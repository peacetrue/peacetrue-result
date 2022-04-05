package com.github.peacetrue.result.exception.jackson;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ClassifiedResultCode;

import javax.annotation.Nullable;

/**
 * {@link JsonEOFException} 异常转换器。
 *
 * @author peace
 */
public class JsonEOFExceptionConverter
        extends AbstractExceptionConverter<JsonEOFException>
        implements ClassifiedResultCode {

    @Nullable
    @Override
    protected Object resolveArgs(JsonEOFException exception) {
        //JSON parse error: Unexpected end-of-input in field name
        //JsonLocation location = exception.getLocation();
        //return new JsonInvalidInfo(location.getLineNr(), location.getColumnNr() - 1, '0', exception.getMessage());
        return null;
    }

    @Override
    public String getSupperCode() {
        return ResultTypes.PARAMETER_INVALID.getCode();
    }

}
