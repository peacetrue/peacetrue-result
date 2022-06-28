package com.github.peacetrue.result;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author peace
 **/
class DataResultExceptionTest {

    private final static EasyRandom EASY_RANDOM = new EasyRandom();

    @Test
    void constructor() {
        GenericDataResult dataResult = EASY_RANDOM.nextObject(GenericDataResult.class);
        DataResultException exception = new DataResultException(dataResult.getCode(), dataResult.getMessage(), dataResult.getData());
        Assertions.assertEquals(dataResult.toString(), exception.toString());
    }
}
