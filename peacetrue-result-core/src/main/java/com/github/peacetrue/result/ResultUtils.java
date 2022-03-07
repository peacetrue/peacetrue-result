package com.github.peacetrue.result;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * 响应结果工具类
 *
 * @author peace
 */
public abstract class ResultUtils {

    /** 抽象工具类，无需实例化 */
    protected ResultUtils() {
    }

    /**
     * 转换响应结果为字符串
     *
     * @param result 响应结果
     * @return 响应结果的字符串描述
     */
    public static String toString(Result result) {
        return String.format("%s:%s", result.getCode(), result.getMessage());
    }

    /**
     * 转换数据响应结果为字符串
     *
     * @param dataResult 数据响应结果
     * @return 数据响应结果的字符串描述
     */
    public static String toString(DataResult<?> dataResult) {
        return String.format("%s[%s]", toString((Result) dataResult), toString(dataResult.getData()));
    }

    private static String toString(Object data) {
        if (data == null) return "null";
        return data.getClass().isArray()
                ? Arrays.toString((Object[]) data)
                : data.toString();
    }

    /**
     * 构造响应结果
     *
     * @param code    响应结果编码
     * @param message 响应结果描述
     * @param data    响应结果数据
     * @return 响应结果
     */
    public static Result build(String code, String message, @Nullable Object data) {
        return data == null ? new ResultImpl(code, message) : new DataResultImpl<>(code, message, data);
    }

    /**
     * 构造响应结果
     *
     * @param resultType 响应结果类型
     * @param data       响应结果数据
     * @return 响应结果
     */
    public static Result build(ResultType resultType, @Nullable Object data) {
        return build(resultType.getCode(), resultType.getName(), data);
    }

    /**
     * build result
     *
     * @param result the result
     * @param data   the data
     * @return a result
     */
    public static Result build(Result result, @Nullable Object data) {
        return data == null ? result : new DataResultImpl<>(result, data);
    }

    /**
     * wrap result
     *
     * @param result the result
     * @return the wrapped result
     */
    public static Result wrap(Result result) {
        return result instanceof DataResult
                ? new DataResultImpl<>((DataResult<?>) result)
                : new ResultImpl(result);
    }

    /**
     * 转换响应结果
     *
     * @param result 响应结果
     * @param <T>    数据类型
     * @return 数据响应结果
     */
    @SuppressWarnings("unchecked")
    public static <T> DataResult<T> cast(Result result) {
        return (DataResult<T>) result;
    }

    /** 获取响应结果的数据 */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getData(Result result) {
        if (result instanceof DataResult) return (T) ((DataResult<?>) result).getData();
        return null;
    }

}
