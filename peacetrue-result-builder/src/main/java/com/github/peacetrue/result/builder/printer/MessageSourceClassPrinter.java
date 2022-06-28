package com.github.peacetrue.result.builder.printer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 * 基于 {@link MessageSource} 的类打印器。
 * 从 {@link MessageSource} 中获取类全限定名称对应的描述。
 *
 * @author peace
 */
@Slf4j
public class MessageSourceClassPrinter implements ClassPrinter {

    public static final String DEFAULT_CLASS_PREFIX = "Class";

    private final MessageSource messageSource;
    @Nullable
    private final String classPrefix;

    public MessageSourceClassPrinter(MessageSource messageSource, @Nullable String classPrefix) {
        this.classPrefix = classPrefix;
        this.messageSource = Objects.requireNonNull(messageSource);
    }

    /**
     * 打印类的描述信息。如果当前类未配置，则查找其父类。
     *
     * @param clazz  类
     * @param locale 方言
     * @return 描述信息
     */
    @Override
    public String print(Class<?> clazz, Locale locale) {
        log.info("get '{}' message' for class '{}'", locale, clazz.getName());
        String message;
        String localClassPrefix = StringUtils.hasLength(classPrefix) ? (classPrefix + ".") : "";
        Set<String> codes = new LinkedHashSet<>();
        Class<?> currentClass = clazz;
        while (true) {
            String code = localClassPrefix + currentClass.getName();
            message = getMessage(code, locale);
            if (message != null) {
                log.debug("got message: {} = {}", code, message);
                return message;
            }
            codes.add(code);
            if (currentClass == Object.class) break;
            currentClass = currentClass.getSuperclass();
        }
        log.warn("unable to get message by {}, default to Class.simpleName '{}'", codes, clazz.getSimpleName());
        return clazz.getSimpleName();
    }

    /**
     * 获取描述信息
     *
     * @param code   编码
     * @param locale 方言
     * @return 描述信息
     */
    @Nullable
    private String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, null, locale);
    }

}
