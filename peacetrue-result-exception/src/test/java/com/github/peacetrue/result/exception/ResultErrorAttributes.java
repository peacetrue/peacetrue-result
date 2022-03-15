//package com.github.peacetrue.result.exception;
//
//import com.github.peacetrue.result.DataResultImpl;
//import com.github.peacetrue.result.Result;
//import com.github.peacetrue.result.ResultCustomizer;
//import com.github.peacetrue.result.ResultTypes;
//import com.github.peacetrue.result.builder.ResultMessageBuilder;
//import com.github.peacetrue.spring.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
//import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.context.request.RequestAttributes;
//
//import java.util.Map;
//import java.util.Objects;
//
///**
// * 响应结果错误属性。
// *
// * @author peace
// */
//public class ResultErrorAttributes extends DefaultErrorAttributes {
//
//    private ExceptionConvertService exceptionConvertService;
//    private ResultMessageBuilder resultMessageBuilder;
//    private ResultCustomizer resultCustomizer;
//
//    @Override
//    public Map<String, Object> getErrorAttributes(RequestAttributes webRequest, boolean includeStackTrace) {
//        Exception error = (Exception) getError(webRequest);
//        if (error != null) return customize(exceptionConvertService.convert(error));
//
//        Integer status = (Integer) webRequest.getAttribute("javax.servlet.error.status_code", RequestAttributes.SCOPE_REQUEST);
//        if (Objects.equals(status, HttpStatus.NOT_FOUND.value())) {
//            String code = ResultTypes.RESOURCE_NOT_FOUND.getCode();
//            Object[] args = {webRequest.getAttribute("javax.servlet.error.request_uri", RequestAttributes.SCOPE_REQUEST)};
//            String message = resultMessageBuilder.build(ResultTypes.RESOURCE_NOT_FOUND.getCode(), args);
//            return customize(new DataResultImpl<>(code, message, args));
//        }
//
//        return super.getErrorAttributes(webRequest, includeStackTrace);
//    }
//
//    private Map<String, Object> customize(Result result) {
//        Object customResult = resultCustomizer.customize(result);
//        return BeanUtils.getPropertyValues(customResult);
//    }
//
//    @Autowired
//    public void setExceptionConvertService(ExceptionConvertService exceptionConvertService) {
//        this.exceptionConvertService = exceptionConvertService;
//    }
//
//    @Autowired
//    public void setResultMessageBuilder(ResultMessageBuilder resultMessageBuilder) {
//        this.resultMessageBuilder = resultMessageBuilder;
//    }
//
//    @Autowired
//    public void setResultCustomizer(ResultCustomizer resultCustomizer) {
//        this.resultCustomizer = resultCustomizer;
//    }
//}
