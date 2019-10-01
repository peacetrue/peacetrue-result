package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.DataResultImpl;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultType;
import com.github.peacetrue.result.payload.PathBean;
import com.github.peacetrue.spring.expression.MessageFormatter;
import com.github.peacetrue.spring.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

/**
 * 异常时，返回{@link Result}信息
 *
 * @author xiayx
 */
public class ResultErrorAttributes extends DefaultErrorAttributes {

    @Autowired
    private ExceptionConvertService exceptionConvertService;
    @Autowired
    private MessageFormatter messageFormatter;

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Throwable error = getError(requestAttributes);
        if (error != null) {
            Result result = exceptionConvertService.convert(error);
            return BeanUtils.map(result);
        }

        Integer status = (Integer) requestAttributes.getAttribute("javax.servlet.error.status_code", RequestAttributes.SCOPE_REQUEST);
        if (status == HttpStatus.NOT_FOUND.value()) {
            String path = (String) requestAttributes.getAttribute("javax.servlet.error.request_uri", RequestAttributes.SCOPE_REQUEST);
            PathBean bean = new PathBean(path);

            DataResultImpl<PathBean> result = new DataResultImpl<>(
                    ResultType.path_not_found.getCode(),
                    messageFormatter.format(ResultType.path_not_found.getMessage(), bean),
                    bean
            );
            return BeanUtils.map(result);
        }


        return null;
    }
}
