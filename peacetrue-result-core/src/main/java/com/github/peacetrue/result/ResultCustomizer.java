package com.github.peacetrue.result;

/**
 * 响应结果定制器。
 * <p>
 * 如果响应结果的结构不满足需求，可自定义成实际需要的结构。
 * 比如：添加 {@code success} 属性或者使用 {@code msg} 替代 {@link Result#getMessage()} 。
 *
 * @author peace
 **/
public interface ResultCustomizer {

    /**
     * 自定义响应结果
     *
     * @param result 响应结果
     * @return 自定义的响应结果
     */
    Object customize(Result result);

    /**
     * 组合两个响应结果定制器，当前对象先执行，参数对象后执行。
     * 参数对象必须返回 {@link Result}，否则不能使用该方法。
     *
     * @param after 后执行的响应结果定制器
     * @return 组合的响应结果定制器
     */
    default ResultCustomizer andThen(ResultCustomizer after) {
        return result -> after.customize((Result) customize(result));
    }
}
