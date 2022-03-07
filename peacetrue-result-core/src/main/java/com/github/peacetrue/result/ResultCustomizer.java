package com.github.peacetrue.result;

/**
 * 响应结果定制器。
 * <p>
 * 如果响应结果的结构不满足需求，可自定义成实际需要的结构。
 * 比如：添加 {@code success} 属性或者使用 {@code msg} 替代 {@link Result#getMessage()} 。
 *
 * @author peace
 * @since 1.0
 **/
public interface ResultCustomizer {

    /**
     * 自定义响应结果
     *
     * @param result 响应结果
     * @return 自定义的响应结果
     */
    Object customize(Result result);

}
