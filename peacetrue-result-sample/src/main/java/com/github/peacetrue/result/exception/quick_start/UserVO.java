package com.github.peacetrue.result.exception.quick_start;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author peace
 **/
@Getter
@Setter
public class UserVO implements Serializable {
    private Long id;
    private String name;
}
