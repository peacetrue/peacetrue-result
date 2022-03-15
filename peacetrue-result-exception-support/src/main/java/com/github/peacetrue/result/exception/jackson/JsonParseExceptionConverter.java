package com.github.peacetrue.result.exception.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;

import javax.annotation.Nullable;

/**
 * 不是有效的 JSON 格式时，抛出此异常。
 *
 * @author peace
 * @see InvalidFormatExceptionConverter
 */
public class JsonParseExceptionConverter
        extends AbstractExceptionConverter<JsonParseException> {

    @Nullable
    @Override
    protected Object[] resolveArgs(JsonParseException exception) {
        //Unexpected character ('{' (code 123))
        //was expecting double-quote to start field name
        return new Object[]{
                exception.getMessage(),
                exception.getLocation().getLineNr(),
                exception.getLocation().getColumnNr()
        };
    }
}
