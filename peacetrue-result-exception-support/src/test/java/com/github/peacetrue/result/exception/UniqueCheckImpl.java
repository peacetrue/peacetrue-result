package com.github.peacetrue.result.exception;

import com.github.peacetrue.validation.constraints.unique.UniqueChecker;

import javax.annotation.Nullable;

/**
 * @author peace
 * 
 **/
public class UniqueCheckImpl implements UniqueChecker {

    @Override
    public boolean check(@Nullable Object id, Object unique) {
        return false;
    }
}
