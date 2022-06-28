package com.github.peacetrue.result;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author peace
 **/
class ResultExceptionTest {

    private final static EasyRandom EASY_RANDOM = new EasyRandom();

    @Test
    void constructor() {
        ResultImpl result = EASY_RANDOM.nextObject(ResultImpl.class);
        ResultException exception = new ResultException(result.getCode(), result.getMessage());
        Assertions.assertEquals(result.toString(), exception.toString());
        Assertions.assertEquals(result.toString(), new ResultException(result).toString());
    }
}
