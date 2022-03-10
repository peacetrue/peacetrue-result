package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.ResultTypes;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author peace
 * @since 1.0
 **/
class ConfiguredResultCodeClassifierTest {

    @Test
    void classifyResultCode() {
        String superCode = ResultTypes.PARAMETER_MISSING.getCode();
        String code = "MissingServletRequestParameter";
        ConfiguredResultCodeClassifier classifier = new ConfiguredResultCodeClassifier(ImmutableMap.of(
                superCode, new HashSet<>(Arrays.asList(code))
        ));
        code = classifier.classifyResultCode(code + ".code");
        Assertions.assertEquals(superCode + ".code", code);
    }
}
