package com.github.peacetrue.result.exception.signature;

import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ClassifiedResultCode;
import com.github.peacetrue.signature.NonceInvalidException;

import javax.annotation.Nullable;

/**
 * {@link NonceInvalidException} 异常转换器。
 *
 * @author peace
 **/
public class NonceInvalidExceptionConverter
        extends AbstractExceptionConverter<NonceInvalidException>
        implements ClassifiedResultCode {

    @Override
    public String getSupperCode() {
        return ResultTypes.PARAMETER_ILLEGAL.getCode();
    }

    @Nullable
    @Override
    protected Object resolveArgs(NonceInvalidException exception) {
        return BeanUtils.toBeanMap(exception);
    }
}
