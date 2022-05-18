package com.taskservice.exception;

import com.taskservice.constants.ErrorMessages;

public class UserLoginAlreadyExistException extends Exception {
    public UserLoginAlreadyExistException(String login) {
        super(String.format(ErrorMessages.USER_LOGIN_ALREADY_EXIST, login));
    }
}
