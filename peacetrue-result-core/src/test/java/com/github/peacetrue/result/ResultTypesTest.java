package com.github.peacetrue.result;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author peace
 **/
class ResultTypesTest {

    @Test
    void isSuccess() {
        Assertions.assertTrue(ResultTypes.isSuccess(ResultTypes.SUCCESS.getCode()));
        Assertions.assertFalse(ResultTypes.isSuccess(ResultTypes.FAILURE.getCode()));
    }
}
