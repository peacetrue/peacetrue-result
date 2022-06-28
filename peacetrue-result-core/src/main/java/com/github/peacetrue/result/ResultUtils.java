package com.github.peacetrue.result;

import com.github.peacetrue.beans.properties.code.CodeAware;
import com.github.peacetrue.beans.properties.data.DataAware;
import com.github.peacetrue.beans.properties.data.DataCapable;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * 响应结果工具类。
 *
 * @author peace
 */
public class ResultUtils {

    private ResultUtils() {
    }

    /**
     * 转换响应结果为字符串。
     *
     * @param result 响应结果
     * @return 响应结果的字符串描述
     */
    public static String toString(Result result) {
        return String.format("%s: %s", result.getCode(), result.getMessage());
    }

    /**
     * 转换数据响应结果为字符串。
     *
     * @param dataResult 数据响应结果
     * @return 数据响应结果的字符串描述
     */
    public static String toString(DataResult<?> dataResult) {
        return String.format("%s ( %s )", toString((Result) dataResult), toString(dataResult.getData()));
    }

    private static String toString(Object data) {
        if (data == null) return "<null>";
        return data.getClass().isArray() ? Arrays.toString((Object[]) data) : data.toString();
    }

    /**
     * 构造响应结果，根据有无响应结果数据选择不同的响应结果类。
     *
     * @param code    响应结果编码
     * @param message 响应结果描述
     * @param data    响应结果数据
     * @return 响应结果
     */
    public static Result build(String code, @Nullable String message, @Nullable Object data) {
        return data == null ? new ResultImpl(code, message) : new DataResultImpl<>(code, message, data);
    }

    /**
     * 构造响应结果。
     *
     * @param resultType 响应结果类型
     * @param data       响应结果数据
     * @return 响应结果
     */
    public static Result build(ResultType resultType, @Nullable Object data) {
        return build(resultType.getCode(), resultType.getName(), data);
    }

    /**
     * 构造响应结果，复制一份。
     * 将异常响应结果转换成非异常响应结果时使用。
     *
     * @param result 响应结果
     * @return 复制的响应结果
     */
    public static Result build(Result result) {
        return result instanceof DataResult
                ? new DataResultImpl<>((DataResult<?>) result)
                : new ResultImpl(result);
    }

    /**
     * 构造响应结果，用于重新设置响应结果编码。
     *
     * @param result 响应结果
     * @param code   响应结果编码
     * @return 响应结果
     */
    public static Result setCode(Result result, String code) {
        if (result instanceof CodeAware) {
            ((CodeAware) result).setCode(code);
            return result;
        }
        return result instanceof DataResult
                ? new DataResultImpl<>(code, result.getMessage(), ((DataResult<?>) result).getData())
                : new ResultImpl(code, result.getMessage());
    }

    /**
     * 构造响应结果，用于重新设置响应结果数据。
     *
     * @param result 响应结果
     * @param data   响应结果数据
     * @return 响应结果
     */
    @SuppressWarnings("unchecked")
    public static Result setData(Result result, @Nullable Object data) {
        if (data == null) return result instanceof DataCapable ? new ResultImpl(result) : result;
        if (!(result instanceof DataAware)) return new DataResultImpl<>(result, data);
        ((DataAware<Object>) result).setData(data);
        return result;
    }

    /**
     * 获取响应结果的数据。
     *
     * @param result 响应结果
     * @param <T>    响应结果数据类型
     * @return 响应结果数据
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T getData(Result result) {
        return result instanceof DataResult ? (T) ((DataResult<?>) result).getData() : null;
    }

}
