package com.github.peacetrue.result.builder;

import com.github.peacetrue.result.builder.printer.MessageSourceClassPrinter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Map;

/**
 * 响应结果构建者配置属性
 *
 * @author peace
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "peacetrue.result.builder")
public class ResultBuilderProperties {

    /** 响应结果编码前缀 */
    private String codePrefix = MessageSourceResultMessageBuilder.DEFAULT_CODE_PREFIX;
    /** 类名前缀 */
    private String classPrefix = MessageSourceClassPrinter.DEFAULT_CLASS_PREFIX;
    /** 默认描述。通过编码找不到描述时，返回默认描述 */
    private String defaultMessage = MessageSourceResultMessageBuilder.DEFAULT_MESSAGE;
    /** 默认编码和自定义编码的映射 */
    private Map<String, String> customCodes = Collections.emptyMap();

}
