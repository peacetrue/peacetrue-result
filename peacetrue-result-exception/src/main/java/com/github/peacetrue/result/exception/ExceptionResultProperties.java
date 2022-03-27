package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.DataResult;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 响应结果异常配置属性。
 *
 * @author peace
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.result.exception")
public class ExceptionResultProperties {

    /** 禁用控制层异常处理器，默认 false */
    private boolean disableControllerExceptionHandler = false;
    /** 嵌套异常类集合 */
    private Set<Class<? extends Throwable>> nestClasses = Collections.emptySet();
    /** 分类的响应结果编码，key 为父类编码，value 为子类编码 */
    private Map<String, Set<String>> classifiedCodes = Collections.emptyMap();
    /** 包含异常堆栈信息，默认 false */
    private Boolean includeStackTrace = false;
    /** 保留 {@link DataResult#getData()}，默认 false */
    private Boolean retainResultData = false;

}
