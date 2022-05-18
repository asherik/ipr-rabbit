package com.taskservice.service;

import com.taskservice.dto.request.UserRegRequest;
import com.taskservice.exception.UserLoginAlreadyExistException;

public interface UserService {

    /**
     * Создать пользователя.
     * @param userRegRequest {@link UserRegRequest}
     */
    void createUser(UserRegRequest userRegRequest) throws UserLoginAlreadyExistException;

}
