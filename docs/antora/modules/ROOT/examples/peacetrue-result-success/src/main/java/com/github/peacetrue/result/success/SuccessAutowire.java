package com.github.peacetrue.result.success;

import com.github.peacetrue.result.DataResult;

import java.lang.annotation.*;

/**
 * 成功响应数据自动包装成 {@link DataResult}
 *
 * @author peace
 * @see SuccessAutowireResponseBodyAdvice
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SuccessAutowire {

    /**
     * 是否禁用自动包装功能
     *
     * @return true 表示禁用
     */
    boolean disabled() default false;

}
