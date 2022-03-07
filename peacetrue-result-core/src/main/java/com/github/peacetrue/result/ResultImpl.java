package com.github.peacetrue.result;

import com.github.peacetrue.beans.properties.code.CodeAware;
import com.github.peacetrue.beans.properties.message.MessageAware;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 响应结果的基本实现
 *
 * @author peace
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultImpl implements Result, CodeAware, MessageAware {

    private String code;
    private String message;

    public ResultImpl(Result result) {
        this(result.getCode(), result.getMessage());
    }

    public String toString() {
        return ResultUtils.toString(this);
    }
}
