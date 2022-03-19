package com.github.peacetrue.result;

import com.github.peacetrue.beans.properties.code.CodeCapable;

/**
 * 响应结果声明
 *
 * @author peace
 **/
public interface ResultDeclare extends CodeCapable {

    /**
     * 获取描述模板。
     *
     * @return 描述模板
     */
    String getMessageTemplate();

    /**
     * 获取描述模板参数类型。
     *
     * @return 描述模板参数类型
     */
    Class<?> getMessageTemplateArgsType();

}
