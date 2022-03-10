package com.github.peacetrue.result;

import com.github.peacetrue.result.builder.MessageSourceResultMessageBuilder;
import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@link MessageSourceResultMessageBuilder} 测试类
 *
 * @author peace
 */
@SpringBootTest(
        classes = {
                ResultMessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class
        }
)
class MessageSourceResultBuilderTest {

    @Autowired
    private MessageSourceResultMessageBuilder resultMessageBuilder;

    @Test
    void build() {
        String code = "MethodArgumentTypeMismatchException";
        Object[] arguments = {"id", "a", "数值"};
        String message = resultMessageBuilder.build(code, arguments);
        String format = "参数\"id\"的值\"a\"无法被转换成\"数值\"类型";
        Assertions.assertEquals(format, message);
    }


}
