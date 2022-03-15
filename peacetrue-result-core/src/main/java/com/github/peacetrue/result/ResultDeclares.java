package com.github.peacetrue.result;

import lombok.Getter;

import java.util.Collection;
import java.util.List;

/**
 * 通用响应结果声明。无实际用处，仅供查阅。
 *
 * @author peace
 **/
@Getter
public enum ResultDeclares implements ResultDeclare {

    /** 请求资源不存在 */
    RESOURCE_NOT_FOUND("请求资源'{0}'不存在", Object[].class),
    /** 多项错误，操作返回多项错误 */
    ERRORS("共'#{#root.size()}'项错误", List.class),
    /** 参数缺失，请求时没有传递必须的参数 */
    PARAMETER_MISSING("缺少必须的参数'#{name}'", Parameter.class),
    /** 参数无效，请求时传递的参数无法转换成后台需要的类型，比如：传递的字符 'h' 无法被转换为数值 */
    PARAMETER_INVALID("参数'#{name}'的值'#{value}'不是'#{type}'类型", Parameter.class),
    /** 参数非法，请求时传递的参数不满足后台的校验规则，比如：传递的数字 '10' 不在要求的 [0,3] 之间 */
    PARAMETER_ILLEGAL("参数'#{name}'的值'#{value}'非法", Parameter.class),
    ;

    private final String messageTemplate;
    private final Class<?> messageTemplateArgsType;

    ResultDeclares(String messageTemplate, Class<?> messageArgs) {
        this.messageTemplate = messageTemplate;
        this.messageTemplateArgsType = messageArgs;
    }

    /** 获取响应结果编码，使用小写的枚举名称作为响应结果编码 */
    public String getCode() {
        return name().toLowerCase();
    }

}
