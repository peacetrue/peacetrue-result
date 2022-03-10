package com.github.peacetrue.result;

import com.github.peacetrue.beans.properties.name.NameCapable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;

/**
 * 代表一个请求参数。
 *
 * @param <T> 参数类型
 * @param <V> 参数值类型
 * @author peace
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Parameter<T, V> implements NameCapable {

    /** 参数名 */
    private String name;
    /** 参数类型 */
    @Nullable
    private T type;
    /** 参数值 */
    @Nullable
    private V value;

    public Parameter(String name, @Nullable T type, @Nullable V value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }
}
