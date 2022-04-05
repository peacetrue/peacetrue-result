package com.github.peacetrue.result.exception.quick_start;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author peace
 **/
@Getter
@Setter
public class UserDTO implements Serializable {
    private String name;
    private String password;
}
