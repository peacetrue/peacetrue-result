package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.exception.AbstractExceptionConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 使用 {@link RequestBody} 接收 JSON 参数时，参数无法有效转换为对象时，会抛出 {@link HttpMessageNotReadableException}。
 * 此异常为包装异常，解析依赖于 {@link HttpMessageNotReadableException#getCause()} 。
 *
 * @author peace
 **/
public class HttpMessageNotReadableExceptionConverter
        extends AbstractExceptionConverter<HttpMessageNotReadableException> {
}
