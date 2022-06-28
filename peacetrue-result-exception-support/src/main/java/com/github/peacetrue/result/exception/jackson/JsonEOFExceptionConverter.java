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

    @Override
    public String getSupperCode() {
        return ResultTypes.PARAMETER_INVALID.getCode();
    }

}
