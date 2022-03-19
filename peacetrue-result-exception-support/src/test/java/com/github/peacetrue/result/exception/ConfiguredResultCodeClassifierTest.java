package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.exception.ConfiguredResultCodeClassifier;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author peace
 **/
class ConfiguredResultCodeClassifierTest {

    @Test
    void classifyResultCode() {
        String superCode = ResultTypes.PARAMETER_MISSING.getCode();
        String code = "MissingServletRequestParameter";
        ConfiguredResultCodeClassifier classifier = new ConfiguredResultCodeClassifier(ImmutableMap.of(
                superCode, new HashSet<>(Collections.singletonList(code))
        ));
        code = classifier.classifyResultCode(code + ".code").get();
        Assertions.assertEquals(superCode + ".code", code);
    }


}
