package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.ResultCustomizer;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import com.github.peacetrue.spring.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.Map;

/**
 * 响应结果错误属性。处理 {@link ResultTypes#RESOURCE_NOT_FOUND} 的场景。
 *
 * @author peace
 * @see org.springframework.boot.web.servlet.error.ErrorAttributes
 */
public final class ResultErrorAttributes {

    public static final ResultErrorAttributes DEFAULT = new ResultErrorAttributes();
    private ResultMessageBuilder resultMessageBuilder;
    private ResultCustomizer resultCustomizer;

    /**
     * 处理 RESOURCE_NOT_FOUND 响应结果。
     *
     * @param webRequest 请求
     * @return 响应结果键值对
     */
    public Map<String, Object> handleResourceNotFound(WebRequest webRequest) {
        String code = ResultTypes.RESOURCE_NOT_FOUND.getCode();
        String uri = (String) webRequest.getAttribute("javax.servlet.error.request_uri", RequestAttributes.SCOPE_REQUEST);
        Map<String, String> messageTemplateArgs = Collections.singletonMap("uri", uri);
        String message = resultMessageBuilder.build(ResultTypes.RESOURCE_NOT_FOUND.getCode(), messageTemplateArgs);
        return convert(resultCustomizer.customize(new DataResultImpl<>(code, message, messageTemplateArgs)));
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> convert(Object result) {
        return result instanceof Map ? (Map<String, Object>) result : BeanUtils.getPropertyValues(result);
    }

    @Autowired
    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
        this.resultMessageBuilder = resultMessageBuilder;
    }

    @Autowired
    public void setResultCustomizer(ResultCustomizer resultCustomizer) {
        this.resultCustomizer = resultCustomizer;
    }

}
