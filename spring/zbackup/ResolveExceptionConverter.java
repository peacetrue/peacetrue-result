//package com.github.peacetrue.result.exception;
//
//import com.github.peacetrue.util.CollectionUtils;
//import com.github.peacetrue.util.RegexUtils;
//
//import javax.annotation.Nullable;
//import java.util.regex.Pattern;
//
///**
// * 从 {@link Exception#getMessage()} 中解析参数
// *
// * @author peace
// */
//public abstract class ResolveExceptionConverter<T extends Throwable> extends AbstractExceptionConverter<T> {
//
//    @Nullable
//    @Override
//    protected Object resolveArgs(T throwable) {
//        return convertArguments(resolveArguments(throwable.getMessage()));
//    }
//
//    protected String[] resolveArguments(String message) {
//        for (Pattern pattern : getPatterns()) {
//            String[] arguments = RegexUtils.extractValue(pattern, message);
//            if (arguments.length > 0) return arguments;
//        }
//        return CollectionUtils.emptyArray(String.class);
//    }
//
//    protected abstract Pattern[] getPatterns();
//
//    protected abstract Object convertArguments(String[] arguments);
//}
