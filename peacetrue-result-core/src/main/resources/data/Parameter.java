package com.github.peacetrue.result.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 一个请求参数
 *
 * @author peace
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parameter<T, V> implements Serializable {

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
