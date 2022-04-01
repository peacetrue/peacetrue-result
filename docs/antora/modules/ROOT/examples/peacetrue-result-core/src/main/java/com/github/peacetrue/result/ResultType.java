package com.github.peacetrue.result;

import com.github.peacetrue.beans.properties.code.CodeCapable;
import com.github.peacetrue.beans.properties.name.NameCapable;

/**
 * 响应结果类型
 *
 * @author peace
 **/
public interface ResultType extends CodeCapable, NameCapable {

    /**
     * 获取响应结果编码
     *
     * @return 响应结果编码
     * @see Result#getCode()
     */
    @Override
    String getCode();

    /**
     * 获取响应结果名称，名称是对编码的说明，是固定不变的
     *
     * @return 响应结果名称
     * @see Result#getMessage()
     */
    @Override
    String getName();

}
