package com.github.peacetrue.result.exception;

import com.github.peacetrue.beans.properties.name.NameCapable;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultUtils;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * 抽象的异常转换器。
 *
 * @param <T> 异常类型
 * @author peace
 */
@Slf4j
@Getter
public abstract class AbstractExceptionConverter<T extends Throwable> implements ExceptionConverter<T> {

    protected ResultMessageBuilder resultMessageBuilder;

    @Override
    public Result convert(T exception) {
        String code = resolveCode(exception);
        log.debug("got Result.code: {}", code);
        Object args = resolveArgs(exception);
        log.debug("got Result.messageTemplateArgs: {}", args);
        String message = resultMessageBuilder.build(code, args);
        log.debug("got Result.message: {}", message);
        if (args instanceof NameCapable) {
            code += "." + ((NameCapable) args).getName();
            log.debug("got Result.code after appended parameter name: {}", code);
        }
        return ResultUtils.build(code, message, args);
    }

    /**
     * 解析响应结果编码。
     *
     * @param exception 异常
     * @return 响应结果编码
     */
    protected String resolveCode(T exception) {
        return resolveCode(exception.getClass());
    }

    /**
     * 解析描述模板参数。
     *
     * @param exception 异常
     * @return 响应结果数据
     */
    @Nullable
    protected abstract Object resolveArgs(T exception);

    public String getCode() {
        Class<?> exceptionType = ResolvableType.forClass(ExceptionConverter.class, getClass()).resolveGeneric(0);
        return resolveCode(Objects.requireNonNull(exceptionType));
    }

    /**
     * 解析异常对应的响应结果编码，使用删除结尾 Exception 的异常类名作为编码
     *
     * @param clazz 异常类
     * @return 响应结果编码
     */
    public static String resolveCode(Class<?> clazz) {
        return clazz.getSimpleName().replaceFirst("Exception$", "");
    }

    @Autowired
    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
        this.resultMessageBuilder = resultMessageBuilder;
    }
}
