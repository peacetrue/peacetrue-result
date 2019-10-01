package com.github.peacetrue.result;

import com.github.peacetrue.core.NameCapable;
import com.github.peacetrue.result.payload.PathBean;
import lombok.Getter;

import java.util.ArrayList;

/**
 * 通用的响应状态
 *
 * @author xiayx
 */
@Getter
public enum ResultType implements NameCapable, Result {

    /** 成功 */
    success("操作成功"),
    /** 失败 */
    failure("操作失败"),
    /** 请求路径不存在，负载数据 {@link PathBean} */
    path_not_found("请求路径不存在", "请求路径'{path}'不存在"),
    /** 多项错误，负载数据 {@link ArrayList} */
    errors("请求出现多项错误", "请求出现'{#root.size()}'项错误"),
    /** 参数错误 */
    parameter_error("参数错误"),
    /** 参数缺失 */
    parameter_missing("参数缺失", "缺少参数'{name}'"),
    /** 参数格式错误 */
    parameter_format_error("参数格式错误", "参数'{name}'的值'{value}'格式错误"),
    /** 参数格式不匹配 */
    parameter_range_error("参数范围错误", "参数'{0}'的值'{0}'范围错误"),
    ;

    private String name;
    private String message;

    ResultType(String name) {
        this(name, name);
    }

    ResultType(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getCode() {
        return name();
    }

}
