package com.github.peacetrue.result.exception;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Set;

/**
 * 响应结果异常配置属性。
 *
 * @author peace
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.result.exception")
public class ResultExceptionProperties {

    /** 启用控制层异常处理器 */
    private boolean enableExceptionHandler = true;
    /** 嵌套异常类集合 */
    private Set<Class<? extends Throwable>> nestClasses = Collections.emptySet();

}
