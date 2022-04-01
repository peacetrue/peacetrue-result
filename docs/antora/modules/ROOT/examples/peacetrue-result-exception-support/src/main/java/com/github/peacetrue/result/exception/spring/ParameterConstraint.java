package com.github.peacetrue.result.exception.spring;

import com.github.peacetrue.result.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;

/**
 * 带约束条件的参数。
 *
 * @author peace
 **/
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ParameterConstraint<T, V> extends Parameter<T, V> {

    @Nullable
    private Object constraint;

    public ParameterConstraint(String name, @Nullable T type, @Nullable V value, @Nullable Object constraint) {
        super(name, type, value);
        this.constraint = constraint;
    }
}
