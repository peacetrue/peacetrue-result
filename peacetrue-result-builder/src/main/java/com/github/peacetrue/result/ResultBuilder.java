package com.github.peacetrue.result;

import javax.annotation.Nullable;
import java.util.Locale;

/**
 * a Result builder
 *
 * @author xiayx
 */
public interface ResultBuilder {

    /**
     * build {@link Result} from code
     *
     * @param code the custom result code
     * @return the {@link DataResult}
     */
    default Result build(String code) {
        return build(code, null);
    }

    /**
     * build {@link Result} from code and locale
     *
     * @param code   the custom result code
     * @param locale the locale
     * @return the {@link Result}
     */
    Result build(String code, @Nullable Locale locale);


    /**
     * build {@link Result} from code, arguments and data
     *
     * @param code the custom result code
     * @param data the data
     * @param <T>  the type of data
     * @return the {@link DataResult}
     */
    default <T> DataResult<T> build(String code, T data) {
        return build(code, data, null);
    }


    /**
     * build {@link Result} from code, arguments and data
     *
     * @param code   the custom result code
     * @param data   the data
     * @param locale the locale
     * @param <T>    the type of data
     * @return the {@link DataResult}
     */
    <T> DataResult<T> build(String code, T data, @Nullable Locale locale);


    /**
     * build success {@link Result}
     *
     * @return the {@link Result}
     */
    default Result success() {
        return success(null);
    }


    /**
     * build success {@link Result}
     *
     * @param locale the locale
     * @return the {@link Result}
     */
    default Result success(@Nullable Locale locale) {
        return build(ResultType.success.name(), locale);
    }

    /**
     * build success {@link Result} from data
     *
     * @param data the data
     * @param <T>  the type of data
     * @return the {@link DataResult}
     */
    default <T> DataResult<T> success(T data) {
        return success(data, null);
    }


    /**
     * build success {@link Result} from data
     *
     * @param data   the data
     * @param locale the locale
     * @param <T>    the type of data
     * @return the {@link DataResult}
     */
    default <T> DataResult<T> success(T data, @Nullable Locale locale) {
        return build(ResultType.success.name(), data, locale);
    }


    /**
     * build failure {@link Result}
     *
     * @return the {@link Result}
     */
    default Result failure() {
        return failure(null);
    }


    /**
     * build failure {@link Result}
     *
     * @param locale the locale
     * @return the {@link Result}
     */
    default Result failure(@Nullable Locale locale) {
        return build(ResultType.failure.name(), locale);
    }


    /**
     * build failure {@link Result} from data
     *
     * @param data the data
     * @param <T>  the type of data
     * @return the {@link DataResult}
     */
    default <T> DataResult<T> failure(T data) {
        return failure(data, null);
    }

    /**
     * build failure {@link Result} from data
     *
     * @param data   the data
     * @param locale the locale
     * @param <T>    the type of data
     * @return the {@link DataResult}
     */
    default <T> DataResult<T> failure(T data, @Nullable Locale locale) {
        return build(ResultType.failure.name(), data, locale);
    }

}
