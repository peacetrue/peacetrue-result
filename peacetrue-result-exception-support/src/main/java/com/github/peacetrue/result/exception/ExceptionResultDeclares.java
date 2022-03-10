package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.Parameter;
import com.github.peacetrue.result.ResultDeclare;
import lombok.Getter;

import java.util.List;

/**
 * 异常响应结果声明。
 *
 * @author peace
 **/
@Getter
public enum ExceptionResultDeclares implements ResultDeclare {

    MISSING_PATH_VARIABLE("缺少路径变量'{name}'", Parameter.class),
    MISSING_SERVLET_REQUEST_PARAMETER("缺少必须的请求参数'#{name}'", Parameter.class),
    METHOD_ARGUMENT_TYPE_MISMATCH("参数'#{name}'类型不匹配'#{type}'", Parameter.class),
    BIND_EXCEPTION("共'#{root.size}'项错误", List.class),
    ;

    private final String messageTemplate;
    private final Class<?> messageArgsType;

    ExceptionResultDeclares(String messageTemplate, Class<?> messageArgsType) {
        this.messageTemplate = messageTemplate;
        this.messageArgsType = messageArgsType;
    }

    /** 获取响应结果编码，使用小写的枚举名称作为响应结果编码 */
    public String getCode() {
        return name().toLowerCase();
    }

}
