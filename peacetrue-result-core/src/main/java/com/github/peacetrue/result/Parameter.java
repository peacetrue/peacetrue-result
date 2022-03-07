package com.github.peacetrue.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 请求参数
 *
 * @author peace
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parameter<T, V> {

    /** 参数名 */
    private String name;
    /** 参数类型 */
    private T type;
    /** 参数值 */
    private V value;

}
