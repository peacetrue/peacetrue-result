package com.github.peacetrue.result.exception.spring;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 命名的描述。描述关联参数名，Validation 插件返回的描述不包含参数名信息，这里关联两者。
 *
 * @author peace
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NamedMessage implements Serializable {
    private String name;
    private String message;
}
