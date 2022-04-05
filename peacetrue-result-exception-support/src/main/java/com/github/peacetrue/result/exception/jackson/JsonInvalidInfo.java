package com.github.peacetrue.result.exception.jackson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * json 无效信息。期望能拿到整个解析的字符串，错误的字符及其位置。目前，无法获取准确的描述信息。
 *
 * @author peace
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JsonInvalidInfo implements Serializable {

    private int line;
    private int column;
    private char value;
    private String message;

}
