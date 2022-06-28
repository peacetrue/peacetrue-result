package com.github.peacetrue.result.exception.signature;

import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.result.exception.ClassifiedResultCode;
import com.github.peacetrue.signature.SignatureInvalidException;

import javax.annotation.Nullable;

/**
 * {@link SignatureInvalidException} 异常转换器。
 *
 * @author peace
 **/
public class SignatureInvalidExceptionConverter
        extends AbstractExceptionConverter<SignatureInvalidException>
        implements ClassifiedResultCode {

    @Override
    public String getSupperCode() {
        return ResultTypes.PARAMETER_ILLEGAL.getCode();
    }

    @Nullable
    @Override
    protected Object resolveArgs(SignatureInvalidException exception) {
        return BeanUtils.toBeanMap(exception);
    }
}
