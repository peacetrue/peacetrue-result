package com.github.peacetrue.result.exception;

/**
 * 嵌套异常登记处。
 *
 * @author peace
 **/
public interface NestExceptionRegistry {

    /**
     * 注册嵌套异常。
     *
     * @param exceptionClass 异常类
     */
    void registerNestException(Class<? extends Throwable> exceptionClass);

    /**
     * 注册嵌套异常。
     *
     * @param exceptionClass 异常类字符串
     */
    @SuppressWarnings("unchecked")
    default void registerNestException(String exceptionClass) {
        try {
            this.registerNestException((Class<? extends Throwable>) Class.forName(exceptionClass));
        } catch (ClassNotFoundException ignored) {
            //ignored
        }
    }

}
