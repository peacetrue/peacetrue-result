//package com.github.peacetrue.result.exception.spring;
//
//import com.github.peacetrue.result.exception.AbstractExceptionConverter;
//import org.springframework.web.multipart.support.MissingServletRequestPartException;
//
///**
// * @author peace
// */
//public class MissingServletRequestPartExceptionConverter
//        extends AbstractExceptionConverter<MissingServletRequestPartException> {
//
//    @Override
//    protected Object[] resolveArgs(MissingServletRequestPartException exception) {
//        return new Object[]{exception.getRequestPartName()};
//    }
//}
