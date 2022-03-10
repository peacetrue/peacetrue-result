//package com.github.peacetrue.spring.expression;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
///**
// * @author peace
// */
//@SpringBootTest(classes = MessageExpressionAutoConfiguration.class)
//class MessageFormatterImplTest {
//
//    @Autowired
//    private MessageFormatter messageFormatter;
//
//    @Data
//    @AllArgsConstructor
//    public static class Bean1 {
//        private String name;
//    }
//
//    @Test
//    void format() {
//        System.out.println(messageFormatter.format("您好！{name}", new Bean1("小玉")));
//    }
//}
