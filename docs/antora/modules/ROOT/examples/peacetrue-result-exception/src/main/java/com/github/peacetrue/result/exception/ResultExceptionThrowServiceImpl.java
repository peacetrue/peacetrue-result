package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.DataResultException;
import com.github.peacetrue.result.ResultException;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 响应结果异常抛出服务实现。
 *
 * @author peace
 **/
public class ResultExceptionThrowServiceImpl implements ResultExceptionThrowService {

    private ResultMessageBuilder resultMessageBuilder;

    @Override
    public void throwResultException(String code) throws ResultException {
        throwResultException(code, LocaleContextHolder.getLocale());
    }

    @Override
    public void throwResultException(String code, Locale locale) throws ResultException {
        throw new ResultException(code, resultMessageBuilder.build(code, locale));
    }

    @Override
    public void throwDataResultException(String code, Object data) throws DataResultException {
        throwDataResultException(code, data, LocaleContextHolder.getLocale());
    }

    @Override
    public void throwDataResultException(String code, Object data, Locale locale) throws DataResultException {
        throw new DataResultException(code, resultMessageBuilder.build(code, data, locale), data);
    }

    @Autowired
    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
        this.resultMessageBuilder = resultMessageBuilder;
    }
}
