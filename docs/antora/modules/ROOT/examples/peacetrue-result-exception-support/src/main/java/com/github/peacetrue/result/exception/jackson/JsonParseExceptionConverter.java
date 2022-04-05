package com.github.peacetrue.result.exception.jackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ClassifiedResultCode;

import javax.annotation.Nullable;

/**
 * 不是有效的 JSON 格式时，抛出此异常。
 *
 * @author peace
 * @see InvalidFormatExceptionConverter
 */
public class JsonParseExceptionConverter
        extends AbstractExceptionConverter<JsonParseException>
        implements ClassifiedResultCode {

    @Override
    protected String resolveCode(JsonParseException exception) {
        return resolveCode(JsonParseException.class);
    }

    @Nullable
    @Override
    protected Object resolveArgs(JsonParseException exception) {
        //Unexpected character ('?' (code 63)): expected a valid value (number, String, array, object, 'true', 'false' or 'null')
        //Unexpected character ('{' (code 123)) was expecting double-quote to start field name
//        JsonLocation location = exception.getLocation();
//        String[] values = RegexUtils.extractValue("Unexpected character \\('(.)'", exception.getMessage());
//        return new JsonInvalidInfo(location.getLineNr(), location.getColumnNr() - 1, values[0].charAt(0), exception.getMessage());
        //无法获取准确的描述信息，期望能拿到整个解析的字符串，错误的字符及其位置
        return null;
    }

    @Override
    public String getSupperCode() {
        return ResultTypes.PARAMETER_INVALID.getCode();
    }

}
