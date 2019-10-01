package com.github.peacetrue.result.success;

import java.lang.annotation.*;

/**
 * 禁用{@link ResultAutoSuccess}功能
 *
 * @author xiayx
 * @see ResultAutoSuccess
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DisableResultAutoSuccess {

}
