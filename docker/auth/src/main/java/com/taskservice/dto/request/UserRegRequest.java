package com.taskservice.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegRequest {

    /**
     * Фамилия.
     */
    @NotBlank
    private String lastname;

    /**
     * Имя.
     */
    @NotBlank
    private String name;

    /**
     * Отчество.
     */
    @NotBlank
    private String middlename;

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
