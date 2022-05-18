package com.taskservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequest {

    /**
     * Логин.
     */
    @NotBlank
    private String login;

    /**
     * Пароль.
     */
    @NotBlank
    private String password;

}
