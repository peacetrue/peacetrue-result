package com.github.peacetrue.result.success;

import com.github.peacetrue.result.Result;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Set;

/**
 * 成功响应结果配置属性
 *
 * @author peace
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "peacetrue.result.success")
public class ResultSuccessProperties {

    /** 响应结果类型集合，指定哪些类型作为响应结果类型，如果返回数据已经是响应结果类型，不重复封装 */
    private Set<Class<?>> resultTypes = Collections.singleton(Result.class);

    /**
     * 禁用的方法，配置哪些方法禁用成功响应数据自动包装功能，方法名为"全路径类名.方法名"，不区分重载方法。
     * 遗留的系统，无法使用注解 {@link SuccessAutowire} 禁用，可通过配置禁用。
     */
    private Set<String> disabledMethods = Collections.emptySet();

}
