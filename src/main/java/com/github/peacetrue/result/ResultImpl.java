package com.github.peacetrue.result;

import com.github.peacetrue.core.CodeAware;
import lombok.*;

import java.io.Serializable;

/**
 * 标准响应数据实现
 *
 * @author xiayx
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultImpl implements Result, CodeAware, Serializable {

    private static final long serialVersionUID = 0L;

    private String code;
    private String message;

    public ResultImpl(Result result) {
        this(result.getCode(), result.getMessage());
    }

    public String toString() {
        return ResultUtils.toString(this);
    }
}
