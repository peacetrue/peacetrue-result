package com.github.peacetrue.result;

import com.github.peacetrue.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * implement by {@link MessageSource}
 *
 * @author xiayx
 */
public class MessageSourceResultBuilder implements ResultBuilder {

    public static final String DEFAULT_PREFIX = "Result";

    private Logger logger = LoggerFactory.getLogger(getClass());
    private String prefix = DEFAULT_PREFIX;
    private MessageSource messageSource;
    private ExpressionParser expressionParser;

    @Override
    public Result build(String code, @Nullable Locale locale) {
        return buildInternal(code, null, locale);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DataResult<T> build(String code, T data, @Nullable Locale locale) {
        return (DataResult<T>) buildInternal(code, Objects.requireNonNull(data), locale);
    }

    /**
     * build {@link Result} from code, arguments, data and locale
     *
     * @param code   the custom result code
     * @param data   the data
     * @param locale the locale
     * @return the {@link Result}
     */
    protected Result buildInternal(String code, @Nullable Object data, @Nullable Locale locale) {
        logger.info("build Result for code '{}' with data {}", code, data);
        if (locale == null) locale = LocaleContextHolder.getLocale();
        String message = this.resolveMessage(code, data, locale);
        if (message == null) message = "No message found under code '" + code + "' for locale '" + locale + "'.";
        return data == null ? new ResultImpl(code, message) : new DataResultImpl<>(code, message, data);
    }

    protected String resolveMessage(String code, @Nullable Object data, @Nullable Locale locale) {
        String[] codes = resolveCode(code);
        logger.debug("extend code '{}' to codes {}", code, Arrays.toString(codes));
        if (StringUtils.hasText(this.prefix)) CollectionUtils.map(codes, s -> this.prefix + "." + s);
        return getMessage(codes, data, locale);
    }

    protected String[] resolveCode(String code) {
        //com...Exception -> [com...Exception, Exception]
        int index = code.lastIndexOf(".");
        return index == -1 ? new String[]{code} : new String[]{code, code.substring(index + 1)};
    }

    /** find message for multiple code */
    @Nullable
    private String getMessage(String[] codes, @Nullable Object data, Locale locale) {
        for (String code : codes) {
            String message = getMessage(code, locale);
            if (message == null) {
                logger.debug("there is not message found for code '{}'", code);
                continue;
            }
            if (data == null) return message;
            Expression expression = expressionParser.parseExpression(message, ParserContext.TEMPLATE_EXPRESSION);
            return expression.getValue(data, String.class);
        }
        return null;
    }

    /** avoid {@link NoSuchMessageException} */
    @Nullable
    private String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code, null, null, locale);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setExpressionParser(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }
}
