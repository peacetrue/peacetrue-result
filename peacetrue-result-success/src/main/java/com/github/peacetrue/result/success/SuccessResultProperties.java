package com.github.peacetrue.result.success;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author xiayx
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.result.success")
public class SuccessResultProperties {

//    /** 配置禁用{@link ResultAutoSuccess}的方法，key指向类，value指向方法 */
//    private Map<Class, String[]> disabledMethods = new LinkedHashMap<>();
//    private Map<Class, String[]> disabledMethods = new LinkedHashMap<>(); key不能使用class，2.0以上支持
    /** 配置禁用{@link ResultAutoSuccess}的方法，全路径名，类名+'.'+方法名，不支持区分重名方法 */
    private Set<String> disabledMethods = new HashSet<>();

}
