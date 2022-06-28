package com.github.peacetrue.result.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 响应结果异常配置属性。
 *
 * @author peace
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "peacetrue.result.exception")
public class ResultExceptionProperties {

    /** 禁用控制层异常处理器，默认 false */
    private boolean disableControllerExceptionHandler = false;
    /** 嵌套异常类集合 */
    private Set<Class<? extends Throwable>> nestClasses = new LinkedHashSet<>();
    /** 分类的响应结果编码，key 为父类编码，value 为子类编码 */
    private Map<String, Set<String>> classifiedCodes = new LinkedHashMap<>();

}
