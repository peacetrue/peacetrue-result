package com.github.peacetrue.result.exception;

import com.github.peacetrue.beans.properties.name.NameCapable;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultUtils;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;

/**
 * 基础的异常转换器。
 *
 * @param <T> 异常类型
 * @author peace
 * @see AbstractExceptionConverter
 * @see SQLConditionalExceptionConverter
 */
@Slf4j
abstract class BaseExceptionConverter<T extends Throwable> {

    protected ResultMessageBuilder resultMessageBuilder;

    /**
     * 转换异常为响应结果。
     *
     * @param exception 异常
     * @return 响应结果
     */
    public Result standardConvert(T exception) {
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
    protected abstract String resolveCode(T exception);

    /**
     * 解析描述模板参数。
     *
     * @param exception 异常
     * @return 响应结果数据
     */
    @Nullable
    protected Object resolveArgs(T exception) {
        return null;
    }

    @Autowired
    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
        this.resultMessageBuilder = resultMessageBuilder;
    }
}
