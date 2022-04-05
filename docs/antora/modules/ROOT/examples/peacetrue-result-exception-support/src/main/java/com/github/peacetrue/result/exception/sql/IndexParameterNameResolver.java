package com.github.peacetrue.result.exception.sql;

/**
 * 基于数据库索引的参数名解析器。
 *
 * @author peace
 **/
public interface IndexParameterNameResolver {

    /**
     * 解析参数名。
     *
     * @param table 表名
     * @param index 索引名
     * @return 参数名
     */
    String resolveParameterName(String table, String index);

}
