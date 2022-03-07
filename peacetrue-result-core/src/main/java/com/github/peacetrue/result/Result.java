package com.github.peacetrue.result;


import com.github.peacetrue.beans.properties.code.CodeCapable;
import com.github.peacetrue.beans.properties.message.MessageCapable;

import javax.annotation.Nullable;

/**
 * 响应结果，后端接口返回数据到前端的统一格式。
 * {@link Result} 只包含响应结果的状态信息，如果还需要包含具体的负载数据，请使用 {@link DataResult}。
 * 如果需要抛出一个与响应结果相关的异常，请使用 {@link ResultException}。
 *
 * @author peace
 */
public interface Result extends CodeCapable, MessageCapable {

    /**
     * 获取响应结果编码，编码指示本次请求是成功还是失败；
     * 如果是失败，能定位出具体的失败原因。
     * 编码不需要区分大小写，推荐使用小写。
     *
     * @return 响应结果编码
     */
    String getCode();

    /**
     * 获取响应结果描述，描述是对响应结果的具体说明，应该能够直接从描述中准确定位问题。
     * 描述不是对编码的说明，需要包含请求的具体信息，编码的说明参考 {@link ResultType#getName()}。
     *
     * @return 响应结果描述
     */
    @Nullable
    String getMessage();

}
