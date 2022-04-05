package com.github.peacetrue.result.exception.quick_start;

import com.github.peacetrue.result.exception.AbstractExceptionConverter;

import javax.annotation.Nullable;

/**
 * @author peace
 **/
public class UserNotExistExceptionConverter
        extends AbstractExceptionConverter<UserNotExistException> {

    @Override
    protected String resolveCode(UserNotExistException exception) {
        return "user_not_exist";
    }

    @Nullable
    @Override
    protected Object resolveArgs(UserNotExistException exception) {
        return exception.getUser();
    }
}
