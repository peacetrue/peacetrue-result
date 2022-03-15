package com.github.peacetrue.result.exception;

import com.github.peacetrue.result.ResultTypes;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.*;

/**
 * 响应结果异常支持配置属性
 *
 * @author peace
 */
@ConfigurationProperties(prefix = "peacetrue.result.exception")
public class ResultExceptionSupportProperties {

    /** 分类的响应结果编码，key 为父类编码，value 为子类编码 */
    private final Map<String, Set<String>> classifiedCodes = new LinkedHashMap<>(3);

    public ResultExceptionSupportProperties() {
        setDefaultClassifiedCodes();
    }

    private void setDefaultClassifiedCodes() {
        classifiedCodes.put(ResultTypes.FAILURE.getCode(), new HashSet<>(Arrays.asList(
                "NullPointer"
        )));
        classifiedCodes.put(ResultTypes.ERRORS.getCode(), new HashSet<>(Arrays.asList(
                "Bind"
        )));
        classifiedCodes.put(ResultTypes.PARAMETER_MISSING.getCode(), new HashSet<>(Arrays.asList(
                "MissingServletRequestParameter", "MissingPathVariable"
        )));
        classifiedCodes.put(ResultTypes.PARAMETER_INVALID.getCode(), new HashSet<>(Arrays.asList(
                "MethodArgumentTypeMismatch", "TypeMismatch", "InvalidFormat", "JsonParse"
        )));
    }

    public Map<String, Set<String>> getClassifiedCodes() {
        return classifiedCodes;
    }

    public void setClassifiedCodes(Map<String, Set<String>> classifiedCodes) {
        this.classifiedCodes.putAll(classifiedCodes);
    }
}
