package com.github.peacetrue.result.builder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.ConversionService;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeConverter;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration.RESULT_BUILDER_CONVERSION_SERVICE;
import static com.github.peacetrue.result.builder.ResultBuilderAutoConfiguration.RESULT_BUILDER_EXPRESSION_PARSER;

/**
 * 基于 {@link MessageSource} 的响应结果描述构建者。
 * 根据编码在配置文件中查找对应的配置。
 *
 * @author peace
 */
@Slf4j
public class MessageSourceResultMessageBuilder implements ResultMessageBuilder, BeanFactoryAware {

    public static final String DEFAULT_CODE_PREFIX = "Result";
    public static final String DEFAULT_MESSAGE = "missing '%s' message";

    private final String codePrefix;
    private final String defaultMessage;
    private MessageSource messageSource;
    private ExpressionParser expressionParser;
    private ConversionService conversionService;
    private BeanResolver beanResolver;

    public MessageSourceResultMessageBuilder() {
        this(DEFAULT_CODE_PREFIX, DEFAULT_MESSAGE);
    }

    public MessageSourceResultMessageBuilder(String codePrefix, String defaultMessage) {
        this.codePrefix = codePrefix;
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String build(String code, @Nullable Object args) {
        return this.build(code, args, LocaleContextHolder.getLocale());
    }

    @Override
    public String build(String code, @Nullable Object args, Locale locale) {
        log.info("build '{}' Result message with locale '{}'", code, locale);
        String localCode = code;
        if (StringUtils.hasText(this.codePrefix)) {
            localCode = this.codePrefix + "." + code;
            log.debug("set code prefix '{}'", this.codePrefix);
        }
        return Optional.ofNullable(getMessage(localCode, args, locale))
                .orElseGet(() -> String.format(Objects.toString(defaultMessage, DEFAULT_MESSAGE), code));
    }

    /** 获取响应结果描述 */
    @Nullable
    private String getMessage(String code, @Nullable Object args, Locale locale) {
        boolean isArrayArgs = args instanceof Object[];
        String message = getMessage(code, isArrayArgs ? (Object[]) args : null, locale);
        log.debug("get property from i18n resource: '{}' = '{}'", code, message);
        if (message == null || args == null || isArrayArgs) return message;
        StandardEvaluationContext context = getStandardEvaluationContext(args);
        String parsedMessage = expressionParser
                .parseExpression(message, ParserContext.TEMPLATE_EXPRESSION)
                .getValue(context, String.class);
        log.debug("resolve message template placeholder: '{}' -> '{}'", message, parsedMessage);
        return parsedMessage;
    }

    /** 避免 {@link NoSuchMessageException} */
    @Nullable
    private String getMessage(String code, @Nullable Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, null, locale);
    }

    private StandardEvaluationContext getStandardEvaluationContext(Object rootObject) {
        StandardEvaluationContext context = new StandardEvaluationContext(rootObject);
        context.setBeanResolver(beanResolver);
        context.setTypeConverter(new StandardTypeConverter(conversionService));
        return context;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanResolver = new BeanFactoryResolver(beanFactory);
    }

    @Autowired
    @Qualifier(RESULT_BUILDER_CONVERSION_SERVICE)
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    @Qualifier(RESULT_BUILDER_EXPRESSION_PARSER)
    public void setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }
}
