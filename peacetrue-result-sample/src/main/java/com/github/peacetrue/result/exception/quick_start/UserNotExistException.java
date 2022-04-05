package com.github.peacetrue.result.exception.quick_start;

/**
 * @author peace
 **/
public class UserNotExistException extends RuntimeException {

    private final User user;

    public UserNotExistException(User user) {
        super("user not exist");
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
