package com.github.peacetrue.result;

import com.github.peacetrue.core.NameCapable;
import com.github.peacetrue.result.payload.Parameter;
import lombok.Getter;

/**
 * @author xiayx
 */
@Getter
public enum ExtendResultType implements NameCapable, Result {

    /** 路径变量缺失，负载数据{@link Parameter} */
    path_variable_missing("路径变量缺失", "缺少路径变量'{name}'"),
    ;

    private String name;
    private String message;

    ExtendResultType(String name) {
        this(name, name);
    }

    ExtendResultType(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getCode() {
        return name();
    }


}
