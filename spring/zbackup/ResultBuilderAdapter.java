//package com.github.peacetrue.result;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.annotation.Nullable;
//import java.util.Locale;
//
///**
// * 响应结果构建器的适配器
// *
// * @author peace
// */
//@Getter
//@Setter
//public class ResultBuilderAdapter implements ResultMessageBuilder {
//
//    private ResultMessageBuilder resultBuilder;
//
//    public ResultBuilderAdapter() {
//    }
//
//    public ResultBuilderAdapter(ResultMessageBuilder resultBuilder) {
//        this.resultBuilder = resultBuilder;
//    }
//
//    @Override
//    public String build(String code) {
//        return resultBuilder.build(code);
//    }
//
//    @Override
//    public String build(String code, @Nullable Locale locale) {
//        return resultBuilder.build(code, locale);
//    }
//
//    @Override
//    public <T> String build(String code, T data) {
//        return resultBuilder.build(code, data);
//    }
//
//    @Override
//    public <T> String build(String code, T data, @Nullable Locale locale) {
//        return resultBuilder.build(code, data, locale);
//    }
//
//}
