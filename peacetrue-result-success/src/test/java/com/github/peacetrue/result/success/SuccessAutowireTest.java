package com.github.peacetrue.result.success;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.ResultException;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.function.Function;

/**
 * @author peace
 */
@SpringBootTest(
        classes = {
                MessageSourceAutoConfiguration.class,
                ResultMessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                SuccessAutowireAutoConfiguration.class,
                SuccessAutowireTestController.class,
                JacksonAutoConfiguration.class,
                JacksonAtFirstAutoConfiguration.class,
                DispatcherServletAutoConfiguration.class,
                ErrorMvcAutoConfiguration.class,
                WebMvcAutoConfiguration.class,
                ServletWebServerFactoryAutoConfiguration.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class SuccessAutowireTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void enableSuccessAutowire() {
        //修改 RestTemplate 接口，使其支持构造类型，类似 objectMapper.getTypeFactory().constructArrayType()
        //手动根据 DataResultImpl + 具体类型 = 构造包装类型
        //不指定类型，使用 RestTemplate 的默认转换，然后手动强制转换
        Integer output = execute(resultType -> this.restTemplate.getForObject(
                "/enableSuccessAutowire?input={0}", resultType, 1
        ), Integer.class);
        Assertions.assertEquals(1, output);
        //2022-03-12 06:33:53.917  WARN 54791 --- [o-auto-1-exec-1] o.s.c.s.ResourceBundleMessageSource      : ResourceBundle [messages] not found for MessageSource: Can't find bundle for base name messages, locale zh_CN
    }

    public <T> T execute(Function<Class<?>, ?> invoker, Class<T> responseType) {
        DataResultImpl<?> result = (DataResultImpl<?>) invoker.apply(DataResultImpl.class);
        if (!ResultTypes.isSuccess(result.getCode())) throw new ResultException(result);
        return objectMapper.convertValue(result.getData(), responseType);
    }

    @Test
    void disableSuccessAutowireByAnnotation() {
        Integer output = this.restTemplate.getForObject(
                "/disableSuccessAutowireByAnnotation?input={0}", Integer.class, 1
        );
        Assertions.assertEquals(1, output);
    }

    @Test
    void disableSuccessAutowireByConfiguration() {
        Integer output = this.restTemplate.getForObject(
                "/disableSuccessAutowireByConfiguration?input={0}", Integer.class, 1
        );
        Assertions.assertEquals(1, output);
    }

}
