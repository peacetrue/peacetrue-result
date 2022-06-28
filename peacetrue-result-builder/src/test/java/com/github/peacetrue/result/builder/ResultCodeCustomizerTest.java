package com.github.peacetrue.result.builder;

import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.result.ResultTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.github.peacetrue.result.ResultTypes.FAILURE;

/**
 * @author peace
 **/
class ResultCodeCustomizerTest {

    @Test
    void customize() {
        ResultCodeCustomizer codeCustomizer = new ResultCodeCustomizer(Collections.singletonMap(FAILURE.getCode(), "error"));

        ResultImpl result = new ResultImpl(ResultTypes.PARAMETER_ERROR.getCode() + ".name", "");
        ResultImpl customize = (ResultImpl) codeCustomizer.customize(result);
        Assertions.assertEquals(customize.getCode(), result.getCode());

        result = new ResultImpl(ResultTypes.FAILURE.getCode() + ".name", "");
        customize = (ResultImpl) codeCustomizer.customize(result);
        Assertions.assertEquals("error.name", customize.getCode());
    }
}
