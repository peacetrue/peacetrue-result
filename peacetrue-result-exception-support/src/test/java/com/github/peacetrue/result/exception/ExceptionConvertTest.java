package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultException;
import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.exception.spring.SpringResultExceptionAutoConfiguration;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试异常转换
 *
 * @author peace
 */
@SpringBootTest(
        classes = {
                MessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                ResultExceptionAutoConfiguration.class,
                SpringResultExceptionAutoConfiguration.class,
        }
)
class ExceptionConvertTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @Autowired
    private ExceptionConvertService exceptionConvertService;

    @Test
    void resultException() {
        ResultException exception = EASY_RANDOM.nextObject(ResultException.class);
        Result result = exceptionConvertService.convert(exception);
        Assertions.assertEquals(result.toString(), exception.toString());
    }


}
