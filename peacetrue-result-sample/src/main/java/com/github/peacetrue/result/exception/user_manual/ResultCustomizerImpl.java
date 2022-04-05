package com.github.peacetrue.result.exception.user_manual;

import com.github.peacetrue.result.DataResult;
import com.github.peacetrue.result.Result;
import com.github.peacetrue.result.ResultCustomizer;
import com.github.peacetrue.result.ResultTypes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author peace
 **/
//tag::class[]
public class ResultCustomizerImpl implements ResultCustomizer {
    @Override
    public Object customize(Result result) {
        boolean isDataResult = result instanceof DataResult;
        Map<String, Object> values = new LinkedHashMap<>(isDataResult ? 5 : 4);
        values.put("requestId", UUID.randomUUID().toString());//添加 requestId 属性
        values.put("success", ResultTypes.isSuccess(result.getCode()));// 添加 success 属性
        values.put("code", result.getCode());
        values.put("msg", result.getMessage()); // message 改为 msg
        if (isDataResult) values.put("data", ((DataResult<?>) result).getData());
        return values;
    }
}
//end::class[]
