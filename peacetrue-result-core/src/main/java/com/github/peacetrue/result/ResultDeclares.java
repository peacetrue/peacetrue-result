package com.github.peacetrue.result;

import lombok.Getter;

import java.util.Collection;

/**
 * 通用响应结果声明。
 *
 * @author peace
 **/
@Getter
public enum ResultDeclares implements ResultDeclare {

    /** 多项错误，操作返回多项错误 */
    ERRORS("共'#{#root.size()}'项错误", Collection.class),
    /** 请求资源不存在 */
    RESOURCE_NOT_FOUND("请求资源'{0}'不存在", Object[].class),
    /** 参数缺失，请求时没有传递必须的参数 */
    PARAMETER_MISSING("缺少必须的参数'{name}'", Parameter.class),
    /** 参数无效，请求时传递的参数无法转换成后台需要的类型，比如：传递的字符 'h' 无法被转换为数值 */
    PARAMETER_INVALID("参数'{name}'的值'{value}'不是有效的'{type}'类型", Parameter.class),
    /** 参数非法，请求时传递的参数不满足后台的校验规则，比如：传递的数字 '10' 不在要求的 [0,3] 之间 */
    PARAMETER_ILLEGAL("参数'{name}'的值'{value}'不符合要求", Parameter.class),
    ;

    private final String messageTemplate;
    private final Class<?> messageArgsType;

    ResultDeclares(String messageTemplate, Class<?> messageArgs) {
        this.messageTemplate = messageTemplate;
        this.messageArgsType = messageArgs;
    }

    /** 获取响应结果编码，使用小写的枚举名称作为响应结果编码 */
    public String getCode() {
        return name().toLowerCase();
    }

}
