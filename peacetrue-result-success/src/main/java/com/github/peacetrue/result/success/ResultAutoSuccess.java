package com.github.peacetrue.result.success;

import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Nullable;

/**
 * 自动将非标准结构的响应数据转换为标准结构.
 *
 * @author xiayx
 */
@ControllerAdvice
public class ResultAutoSuccess implements ResponseBodyAdvice<Object> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return ResolvableType.forClass(HttpMessageConverter.class, converterType).resolveGeneric(0).isAssignableFrom(Result.class)
                && !returnType.hasMethodAnnotation(DisableResultAutoSuccess.class)
                && !Result.class.isAssignableFrom(returnType.getParameterType())
                && !exclude(returnType);
    }

    /** 不使用自动转换 */
    protected boolean exclude(MethodParameter returnType) {
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        logger.debug("自动转换[{}]为成功标准响应数据", body);
        return this.toSuccessResult(body);
    }

    /** 转换成成功的响应数据 */
    protected Result toSuccessResult(@Nullable Object body) {
        return body == null ? ResultType.success : new DataResultImpl<>(ResultType.success, body);
    }


}
