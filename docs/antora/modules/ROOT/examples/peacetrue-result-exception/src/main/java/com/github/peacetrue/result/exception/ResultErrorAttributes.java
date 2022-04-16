package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.ResultCustomizer;
import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.result.ResultTypes;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import com.github.peacetrue.spring.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Objects;

/**
 * 响应结果错误属性。处理 {@link ResultTypes#RESOURCE_NOT_FOUND} 的场景。
 *
 * @author peace
 */
public class ResultErrorAttributes extends DefaultErrorAttributes {

    private ResultMessageBuilder resultMessageBuilder;
    private ResultCustomizer resultCustomizer;

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Integer status = (Integer) webRequest.getAttribute("javax.servlet.error.status_code", RequestAttributes.SCOPE_REQUEST);
        if (Objects.equals(status, HttpStatus.NOT_FOUND.value())) {
            String code = ResultTypes.RESOURCE_NOT_FOUND.getCode();
            Object[] messageTemplateArgs = {webRequest.getAttribute("javax.servlet.error.request_uri", RequestAttributes.SCOPE_REQUEST)};
            String message = resultMessageBuilder.build(ResultTypes.RESOURCE_NOT_FOUND.getCode(), messageTemplateArgs);
            return convert(resultCustomizer.customize(new ResultImpl(code, message)));
        }

        return super.getErrorAttributes(webRequest, includeStackTrace);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> convert(Object result) {
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
