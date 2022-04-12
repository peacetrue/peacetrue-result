package com.github.peacetrue.result.exception.sql;

import com.github.peacetrue.util.RegexUtils;

import java.util.regex.Pattern;

/**
 * 移除前缀，并转换为驼峰式。
 * <p>
 * uk_zip_code TO zip_code TO zipCode
 *
 * @author peace
 **/
public class IndexParameterNameResolverImpl implements IndexParameterNameResolver {

    private static final Pattern PATTERN = Pattern.compile("_(.)");

    @Override
    public String resolveParameterName(String table, String index) {
        int i = index.indexOf("_");
        index = i == -1 ? index : index.substring(i + 1);
        return RegexUtils.replaceAll(PATTERN, index, matcher -> matcher.group(1).toUpperCase());
    }

}
