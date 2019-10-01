//package com.github.peacetrue.result.exception.web;
//
//import com.github.peacetrue.result.Result;
//import com.github.peacetrue.result.exception.ExceptionConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.BindException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//
///**
// * for {@link MethodArgumentNotValidException}
// *
// * @author xiayx
// */
//public class MethodArgumentNotValidExceptionConverter implements ExceptionConverter<MethodArgumentNotValidException> {
//
//    private ExceptionConverter<BindException> bindExceptionConverter;
//
//    public Result convert(MethodArgumentNotValidException exception) {
//        BindExceptionConverter bindExceptionConverter = (BindExceptionConverter) this.bindExceptionConverter;
//        return bindExceptionConverter.convert(exception.getBindingResult());
//    }
//
//    @Autowired
//    public void setBindExceptionConverter(ExceptionConverter<BindException> bindExceptionConverter) {
//        this.bindExceptionConverter = bindExceptionConverter;
//    }
//}
