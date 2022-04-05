package com.github.peacetrue.result.exception.user_manual;

import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.result.builder.ResultMessageBuilder;
import com.github.peacetrue.result.exception.ExceptionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;

//tag::class[]
public class MissingServletRequestParameterExceptionConverter1
        implements ExceptionConverter<MissingServletRequestParameterException> {

    private ResultMessageBuilder resultMessageBuilder;

    @Override
    public Result convert(MissingServletRequestParameterException exception) {
        String message = resultMessageBuilder.build(
                // 确定一个[响应结果描述模板配置键](简称[描述模板配置键])
                "MissingServletRequestParameter",
                // 使用对象作为[描述模板参数]
                new Parameter<>(exception.getParameterName(), exception.getParameterType(), null)
                // 使用数组作为[描述模板参数]
                //new Object[]{exception.getParameterName()}
        );
        return new ResultImpl(
                // 确定一个[响应结果编码](简称[编码])
                "parameter_missing",
                // [响应结果描述]，由[描述模板配置值]渲染[描述模板参数]后得到
                message
        );
    }

    @Autowired
    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
        this.resultMessageBuilder = resultMessageBuilder;
    }
}
//end::class[]
