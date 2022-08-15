package com.github.peacetrue.result.exception.jackson;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import com.github.peacetrue.result.exception.ResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.ResultExceptionSupportAutoConfiguration;
import com.github.peacetrue.result.exception.TestBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

/**
 * @author peace
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = {
                ResultMessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                JacksonAutoConfiguration.class,

                ResultExceptionAutoConfiguration.class,
                ResultExceptionSupportAutoConfiguration.class,
                JacksonResultExceptionAutoConfiguration.class
        }
)
class JacksonResultExceptionConverterTest {

    @Autowired
    private JsonParseExceptionConverter jsonParseExceptionConverter;
    @Autowired
    private JsonEOFExceptionConverter jsonEOFExceptionConverter;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void convert() {
        LocaleContextHolder.setLocale(Locale.SIMPLIFIED_CHINESE);
        JsonEOFException exception = Assertions.assertThrows(
                JsonEOFException.class, () -> objectMapper.readValue("{", TestBean.class)
        );
        Result result = jsonEOFExceptionConverter.convert(exception);
        Assertions.assertEquals("JsonEOF", result.getCode());
        Assertions.assertEquals("JSON 请求体缺少闭合标记", result.getMessage());
    }
}
