package com.github.peacetrue.result;

import lombok.Getter;

/**
 * 通用响应结果类型
 *
 * @author peace
 */
@Getter
public enum ResultTypes implements ResultType {

    /** 成功 */
    SUCCESS("操作成功"),
    /** 失败，不确定具体失败原因时使用 */
    FAILURE("操作失败"),
    /** 请求资源不存在 */
    RESOURCE_NOT_FOUND("请求资源不存在"),
    /** 参数错误，不确定参数错误的具体失败原因时使用 */
    PARAMETER_ERROR("参数错误"),
    /** 参数缺失，请求时没有传递必须的参数 */
    PARAMETER_MISSING("参数缺失"),
    /** 参数无效，请求时传递的参数无法转换成后台需要的类型，比如：传递的字符 'h' 无法被转换为数值 */
    PARAMETER_INVALID("参数无效"),
    /** 参数非法，请求时传递的参数不满足后台的校验规则，比如：传递的数字 '10' 不在要求的 [0,3] 之间 */
    PARAMETER_ILLEGAL("参数非法"),
    ;

    private final String name;

    ResultTypes(String name) {
        this.name = name;
    }

    /** 获取响应结果编码，使用小写的枚举名称作为响应结果编码 */
    public String getCode() {
        return name().toLowerCase();
    }

    /**
     * 响应结果编码是否为成功
     *
     * @param code 响应结果编码
     * @return true 如果是成功
     */
    public static boolean isSuccess(String code) {
        return SUCCESS.getCode().equals(code);
    }
}
