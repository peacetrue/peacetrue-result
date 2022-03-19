package com.github.peacetrue.result;

import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author peace
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = {
                ResultMessageSourceAutoConfiguration.class,
                MessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
        }
)
class ResultBuilderTest {

    @Autowired
    private ResultMessageBuilder resultMessageBuilder;

    @Test
    void build() {
        String code = "MethodArgumentTypeMismatchException";
        Parameter<Class<?>, String> parameter = new Parameter<>("id", Long.class, "a");
        String message = resultMessageBuilder.build(code, parameter);
        String format = "参数\"id\"的值\"a\"无法被转换成\"长整形数值\"类型";
        Assertions.assertEquals(format, message);
    }

}
