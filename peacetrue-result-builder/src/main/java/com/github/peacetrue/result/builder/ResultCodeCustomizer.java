package com.github.peacetrue.result.builder;

import com.github.peacetrue.beans.properties.code.CodeAware;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultCustomizer;
import com.github.peacetrue.result.ResultUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * 自定义响应结果编码。组件默认返回的编码不满足需求，可通过配置修改。
 *
 * @author peace
 **/
@Slf4j
public class ResultCodeCustomizer implements ResultCustomizer {

    /** 默认编码和自定义编码的映射 */
    private final Map<String, String> customCodes;

    public ResultCodeCustomizer() {
        this(Collections.emptyMap());
    }

    public ResultCodeCustomizer(Map<String, String> customCodes) {
        this.customCodes = Objects.requireNonNull(customCodes);
    }

    @Override
    public Object customize(Result result) {
        log.trace("customize '{}' Result", result.getCode());
        String code = result.getCode().split("\\.", 2)[0];
        log.trace("got Result.code without Parameter.name: '{}'", code);
        if (!customCodes.containsKey(code)) return result;
        String customCode = customCodes.get(code);
        customCode = result.getCode().substring(code.length()) + customCode;
        log.debug("customize Result.code: '{}' -> '{}'", result.getCode(), customCode);
        if (!(result instanceof CodeAware)) return ResultUtils.build(result, customCode);
        ((CodeAware) result).setCode(customCode);
        return result;
    }

}
