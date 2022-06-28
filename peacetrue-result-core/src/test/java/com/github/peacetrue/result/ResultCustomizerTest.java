package com.github.peacetrue.result;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author peace
 **/
class ResultCustomizerTest {

    private final static EasyRandom EASY_RANDOM = new EasyRandom();


    @Test
    void customize() {
        ResultImpl result = EASY_RANDOM.nextObject(ResultImpl.class);
        Assertions.assertSame(result, ResultCustomizer.DEFAULT.customize(result));
    }

    @Test
    void andThen() {
        ResultImpl result = EASY_RANDOM.nextObject(ResultImpl.class);
        Assertions.assertSame(result, ResultCustomizer.DEFAULT.andThen(ResultCustomizer.DEFAULT).customize(result));
    }
}
