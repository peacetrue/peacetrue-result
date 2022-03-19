package com.github.peacetrue.result.exception;

import com.github.peacetrue.beans.properties.name.NameCapable;
import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultUtils;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;

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
        Parameter.clearParameterType(args);
        return ResultUtils.build(code, message, args);
    }

    /**
     * 解析响应结果编码。
     *
     * @param exception 异常
     * @return 响应结果编码
     */
    protected String resolveCode(T exception) {
        return exception.getClass().getSimpleName().replaceFirst("Exception$", "");
    }

    /**
     * 解析描述模板参数。
     *
     * @param exception 异常
     * @return 响应结果数据
     */
    @Nullable
    protected abstract Object resolveArgs(T exception);

    @Autowired
    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
        this.resultMessageBuilder = resultMessageBuilder;
    }
}
