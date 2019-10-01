package com.github.peacetrue.result;


import com.github.peacetrue.core.CodeCapable;
import com.github.peacetrue.core.MessageCapable;

/**
 * 标准响应数据
 *
 * @author xiayx
 */
public interface Result extends CodeCapable, MessageCapable {

    /** 状态编码 */
    String getCode();

    /** 状态描述 */
    String getMessage();
}
