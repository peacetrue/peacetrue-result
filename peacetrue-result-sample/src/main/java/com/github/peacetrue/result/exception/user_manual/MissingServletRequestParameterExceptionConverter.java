package com.github.peacetrue.result.exception.user_manual;

import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultImpl;
import com.github.peacetrue.result.exception.ExceptionConverter;
import org.springframework.web.bind.MissingServletRequestParameterException;

//tag::class[]
public class MissingServletRequestParameterExceptionConverter
        implements ExceptionConverter<MissingServletRequestParameterException> {
    @Override
    public Result convert(MissingServletRequestParameterException exception) {
        return new ResultImpl(
                "parameter_missing", //确定一个响应结果编码
                "缺少必须的请求参数'" + exception.getParameterName() + "'" //给出一个响应结果描述
        );
    }
}
//end::class[]
