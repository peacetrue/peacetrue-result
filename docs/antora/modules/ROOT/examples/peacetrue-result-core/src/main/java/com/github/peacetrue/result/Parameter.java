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

    /**
     * 清除参数类型。
     * 解析描述时类型有用，返回给调用者目前无用，清除掉。
     *
     * @param parameter 参数
     */
    public static void clearParameterType(@Nullable Object parameter) {
        if (parameter instanceof Parameter) {
            ((Parameter<?, ?>) parameter).setType(null);
        }
    }
}
