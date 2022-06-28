package com.github.peacetrue.result.exception.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author peace
 **/
class IndexParameterNameResolverImplTest {

    @Test
    void resolveParameterName() {
        IndexParameterNameResolverImpl resolver = new IndexParameterNameResolverImpl();
        Assertions.assertEquals("zip", resolver.resolveParameterName("table", "zip"));
        Assertions.assertEquals("zip", resolver.resolveParameterName("table", "uk_zip"));
        Assertions.assertEquals("zipCode", resolver.resolveParameterName("table", "uk_zip_code"));
    }
}
