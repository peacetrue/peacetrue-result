package com.github.peacetrue.result.success;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Set;

/**
 * 成功响应结果配置属性
 *
 * @author peace
 */
@ConfigurationProperties(prefix = "peacetrue.result.success")
public class SuccessResultProperties {

    /**
     * 禁用的方法，配置哪些方法禁用成功响应数据自动包装功能，方法名为"全路径类名.方法名"，不区分重载方法。
     * 遗留的系统，无法使用注解 {@link SuccessAutowire} 禁用，可通过配置禁用。
     */
    private Set<String> disabledMethods = Collections.emptySet();

    public Set<String> getDisabledMethods() {
        return disabledMethods;
    }

    public void setDisabledMethods(Set<String> disabledMethods) {
        this.disabledMethods = disabledMethods;
    }
}
