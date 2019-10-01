package com.github.peacetrue.result.exception.net;

import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import com.github.peacetrue.util.URLDecoderUtils;

import javax.annotation.Nullable;
import java.net.UnknownHostException;

/**
 * for {@link UnknownHostException}
 *
 * @author xiayx
 */
public class UnknownHostExceptionConverter extends AbstractExceptionConverter<UnknownHostException, String> {

    @Nullable
    @Override
    protected String resolveData(UnknownHostException exception) {
        return URLDecoderUtils.decode(exception.getMessage());
    }

}
