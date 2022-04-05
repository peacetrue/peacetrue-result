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
public class ResultExceptionProperties {

    /** 禁用控制层异常处理器，默认 false */
    private boolean disableControllerExceptionHandler = false;
    /** 嵌套异常类集合 */
    private Set<Class<? extends Throwable>> nestClasses = Collections.emptySet();
    /** 分类的响应结果编码，key 为父类编码，value 为子类编码 */
    private Map<String, Set<String>> classifiedCodes = Collections.emptyMap();
    /** 在 {@link DataResult#getData()} 中存放异常堆栈信息，默认 false */
    private Boolean includeStackTrace = false;
    /**
     * 在 {@link DataResult#getData()} 中存放描述模板参数，默认 false；
     * 如果 {@link #includeStackTrace} 为 true，此项配置失效。
     */
    private Boolean includeMessageTemplateArgs = false;

}
