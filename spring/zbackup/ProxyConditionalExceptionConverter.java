//package com.github.peacetrue.result.exception;
//
//import com.github.peacetrue.result.Result;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
///**
// * for {@link Throwable#getCause()}
// *
// * @author peace
// */
//@Slf4j
//public class ProxyConditionalExceptionConverter implements ConditionalExceptionConverter {
//
//    private List<Class<? extends Throwable>> proxyClasses;
//    private ExceptionConvertService exceptionConvertService;
//
//    public ProxyConditionalExceptionConverter() {
//    }
//
//    public ProxyConditionalExceptionConverter(List<Class<? extends Throwable>> proxyClasses) {
//        this.proxyClasses = new ArrayList<>(Objects.requireNonNull(proxyClasses));
//    }
//
//    @Override
//    public boolean supports(Throwable throwable) {
//        Throwable cause = throwable.getCause();
//        if (cause == null || cause.equals(throwable)) return false;
//        for (Class<? extends Throwable> proxyClass : proxyClasses) {
//            if (proxyClass.equals(throwable.getClass())) {
//                log.info("the throwable class '{}' proxy for '{}'", throwable.getClass().getName(), cause.getClass().getName());
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public Result convert(Throwable throwable) {
//        return exceptionConvertService.convert(throwable.getCause());
//    }
//
//    public void setProxyClasses(List<Class<? extends Throwable>> proxyClasses) {
//        this.proxyClasses = proxyClasses;
//    }
//
//    @Autowired
//    public void setExceptionConvertService(ExceptionConvertService exceptionConvertService) {
//        this.exceptionConvertService = exceptionConvertService;
//    }
//}
