package com.github.peacetrue.result.exception.signature;

import com.github.peacetrue.lang.UncheckedException;
import com.github.peacetrue.signature.ClientInvalidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.beans.IntrospectionException;
import java.beans.Introspector;

/**
 * @author peace
 **/
class BeanUtilsTest {

    @Test
    void getBeanInfo() {
        MockedStatic<Introspector> mockedStatic = Mockito.mockStatic(Introspector.class);
        mockedStatic.when(() -> Introspector.getBeanInfo(Mockito.any(), Mockito.any(), Mockito.anyInt()))
                .thenThrow(new IntrospectionException("ignored"));
        Assertions.assertThrows(UncheckedException.class, () -> BeanUtils.getBeanInfo(ClientInvalidException.class));
    }
}
