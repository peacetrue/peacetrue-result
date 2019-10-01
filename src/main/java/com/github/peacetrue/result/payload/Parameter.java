package com.github.peacetrue.result.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 一个请求参数
 *
 * @author xiayx
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter<T, V> implements Serializable {

    private static final long serialVersionUID = 0L;

    /** 参数名 */
    private String name;
    /** 参数类型 */
    private T type;
    /** 参数值 */
    private V value;

    public Parameter(String name, T type) {
        this.name = name;
        this.type = type;
    }

}
