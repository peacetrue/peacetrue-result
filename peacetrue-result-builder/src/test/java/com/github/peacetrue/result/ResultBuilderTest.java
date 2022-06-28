package com.github.peacetrue.result;

import com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import com.github.peacetrue.result.builder.ResultMessageSourceAutoConfiguration;
import com.github.peacetrue.result.builder.printer.ClassPrinter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

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
    @Autowired
    private ClassPrinter classPrinter;

    @Test
    void build() {
        String code = "MethodArgumentTypeMismatchException";
        Parameter<Class<?>, String> parameter = new Parameter<>("id", Long.class, "a");
        String message = resultMessageBuilder.build(code, parameter);
        String format = "参数\"id\"的值\"a\"无法被转换成\"长整形数值\"类型";
        Assertions.assertEquals(format, message);

        message = resultMessageBuilder.build(code);
        Assertions.assertEquals("参数\"#{name}\"的值\"#{value}\"无法被转换成\"#{type}\"类型", message);

        message = resultMessageBuilder.build(code, (Object) null);
        Assertions.assertEquals("参数\"#{name}\"的值\"#{value}\"无法被转换成\"#{type}\"类型", message);

        message = resultMessageBuilder.build(code, LocaleContextHolder.getLocale());
        Assertions.assertEquals("参数\"#{name}\"的值\"#{value}\"无法被转换成\"#{type}\"类型", message);

        String classMessage = classPrinter.print(Long.class, LocaleContextHolder.getLocale());
        message = resultMessageBuilder.build("ArrayArgument", new String[]{"id", "a", classMessage});
        Assertions.assertEquals("参数\"id\"的值\"a\"无法被转换成\"长整形数值\"类型", message);
    }

    @Test
    void buildMissing() {
        String code = "Missing";
        String message = resultMessageBuilder.build(code, new String[]{"missing"});
        Assertions.assertEquals("missing 'Missing' message", message);
    }

    @Test
    void classPrinter() {
        Locale locale = LocaleContextHolder.getLocale();
        String message = classPrinter.print(String.class, locale);
        Assertions.assertEquals(String.class.getSimpleName(), message);
    }
}
