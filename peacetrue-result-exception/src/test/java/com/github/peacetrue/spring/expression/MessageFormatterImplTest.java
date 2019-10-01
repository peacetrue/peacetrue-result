package com.github.peacetrue.spring.expression;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageExpressionAutoConfiguration.class)
public class MessageFormatterImplTest {

    @Autowired
    private MessageFormatter messageFormatter;

    @Data
    @AllArgsConstructor
    public static class Bean1 {
        private String name;
    }

    @Test
    public void format() {
        System.out.println(messageFormatter.format("您好！{name}", new Bean1("小玉")));
    }
}