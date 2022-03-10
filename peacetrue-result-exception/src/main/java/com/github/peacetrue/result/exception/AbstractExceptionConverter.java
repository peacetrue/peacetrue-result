package com.github.peacetrue.result.exception;

import com.github.peacetrue.beans.properties.name.NameCapable;
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
        log.info("转换异常'{}'", exception.getClass().getSimpleName());
        String code = resolveCode(exception);
        log.debug("取得响应结果编码'{}'", code);
        Object args = resolveArgs(exception);
        log.debug("取得响应结果描述参数'{}'", args);
        String message = resultMessageBuilder.build(code, args);
        log.debug("取得响应结果描述'{}'", message);
        if (args instanceof NameCapable) {
            code += "." + ((NameCapable) args).getName();
            log.debug("响应结果编码添加参数名'{}'", ((NameCapable) args).getName());
        }
        return ResultUtils.build(code, message, args);
    }

    /**
     * 解析响应结果编码
     *
     * @param throwable 异常
     * @return 响应结果编码
     */
    protected String resolveCode(T throwable) {
        return throwable.getClass().getSimpleName()
                .replaceFirst("Exception$", "");
    }

    /**
     * 解析描述参数
     *
     * @param throwable 异常
     * @return 响应结果数据
     */
    @Nullable
    protected Object resolveArgs(T throwable) {
        return null;
    }

    @Autowired
    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
        this.resultMessageBuilder = resultMessageBuilder;
    }
}
