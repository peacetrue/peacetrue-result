//package com.github.peacetrue.result;
//
//import com.github.peacetrue.core.CodeAware;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.ReflectionUtils;
//
//import javax.annotation.Nullable;
//import java.lang.reflect.Field;
//import java.util.Collection;
//import java.util.Locale;
//import java.util.function.BiFunction;
//
///**
// * used to convert {@link Result#getCode()}
// *
// * @author peace
// */
//@Slf4j
//public class CodeConvertResultBuilder extends ResultBuilderAdapter {
//
//    private static final Field FIELD_CODE = ReflectionUtils.findField(ResultImpl.class, "code");
//
//    static {
//        FIELD_CODE.setAccessible(true);
//    }
//
//    private ResultCodeConverter resultCodeConverter;
//
//    protected <T extends Result> T handleCode(T result, BiFunction<T, String, T> converter) {
//        String resolve = resultCodeConverter.convert(result.getCode());
//        return resolve == null || resolve.equals(result.getCode())
//                ? result
//                : converter.apply(result, resolve);
//    }
//
//    protected <T extends Result> T setCodeChain(T result, String code) {
//        setCode(result, code);
//        return handleData(result);
//    }
//
//    @SuppressWarnings("unchecked")
//    public static void setCode(Result result, String code) {
//        if (result instanceof CodeAware) {
//            setCode((CodeAware) result, code);
//            log.debug("set code from '{}' to '{}'", result.getCode(), code);
//        } else if (result instanceof ResultImpl) {
//            setCode((ResultImpl) result, code);
//            log.debug("set code from '{}' to '{}'", result.getCode(), code);
//        }
//    }
//
//    public static void setCode(ResultImpl result, String code) {
//        ReflectionUtils.setField(FIELD_CODE, result, code);
//    }
//
//    public static void setCode(CodeAware codeAware, String code) {
//        codeAware.setCode(code);
//    }
//
//    @SuppressWarnings("unchecked")
//    protected <T extends Result> T handleData(T result) {
//        if (!(result instanceof DataResult)) return result;
//        Object data = ((DataResult) result).getData();
//        if (!(data instanceof Collection)) return result;
//
//        ((Collection) data).forEach(item -> {
//            if (item instanceof Result) handleCode((Result) item, this::setCodeChain);
//        });
//        return result;
//    }
//
//    @Override
//    public String build(String code) {
//        return handleCode(super.build(code), this::setCodeChain);
//    }
//
//    @Override
//    public String build(String code, @Nullable Locale locale) {
//        return handleCode(super.build(code, locale), this::setCodeChain);
//    }
//
//    @Override
//    public <T> String build(String code, T data) {
//        return handleCode(super.build(code, data), this::setCodeChain);
//    }
//
//    @Override
//    public <T> String build(String code, T data, @Nullable Locale locale) {
//        return handleCode(super.build(code, data, locale), this::setCodeChain);
//    }
//
//    @Autowired
//    public void setResultBuilder(ResultMessageBuilder resultBuilder) {
//        super.setResultBuilder(resultBuilder);
//    }
//
//    @Autowired
//    public void setResultCodeConverter(ResultCodeConverter resultCodeConverter) {
//        this.resultCodeConverter = resultCodeConverter;
//    }
//}
