package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Result;
import org.springframework.core.ResolvableType;

import java.util.Objects;

/**
 * 抽象的异常转换器。
 *
 * @param <T> 异常类型
 * @author peace
 */
public abstract class AbstractExceptionConverter<T extends Throwable>
        extends BaseExceptionConverter<T>
        implements ExceptionConverter<T> {

    /**
     * 解析异常对应的响应结果编码，使用删除结尾 Exception 的异常类名作为编码。
     *
     * @param clazz 异常类
     * @return 响应结果编码
     */
    public static String resolveCode(Class<?> clazz) {
        return clazz.getSimpleName().replaceFirst("Exception$", "");
    }

    @Override
    public Result convert(T exception) {
        return super.standardConvert(exception);
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
     * 获取当前异常响应结果编码。
     *
     * @return 响应结果编码
     */
    public String getCode() {
        Class<?> exceptionType = ResolvableType.forClass(ExceptionConverter.class, getClass()).resolveGeneric(0);
        return resolveCode(Objects.requireNonNull(exceptionType));
    }

}
