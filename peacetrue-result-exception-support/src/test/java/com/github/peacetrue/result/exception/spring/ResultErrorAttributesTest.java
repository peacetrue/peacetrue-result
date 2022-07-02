package com.github.peacetrue.result.exception.spring;

import org.mockito.Mockito;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

/**
 * @author peace
 **/
class ResultErrorAttributesTest {

//    @Test
    void extendDefaultErrorAttributes() throws Exception {
        Class<? extends ErrorAttributes> clazz = ResultErrorAttributesUtils.extendDefaultErrorAttributes();
        ErrorAttributes errorAttributes = clazz.newInstance();
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        Mockito.when(webRequest.getAttribute(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(HttpStatus.NOT_FOUND.value());
//        Assertions.assertThrows(ClassCastException.class, () -> errorAttributes.getErrorAttributes(webRequest, false));
    }

}
