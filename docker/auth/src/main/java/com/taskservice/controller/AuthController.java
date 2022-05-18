package com.taskservice.controller;

import com.taskservice.constants.ApiConstants;
import com.taskservice.dto.request.UserRegRequest;
import com.taskservice.exception.UserLoginAlreadyExistException;
import com.taskservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.V1 + ApiConstants.AUTH)
public class AuthController {
    private final UserService userService;

    @PostMapping(value = ApiConstants.CREATE_USER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody @Valid @NotNull UserRegRequest userRegRequest) throws UserLoginAlreadyExistException {
        userService.createUser(userRegRequest);
        return ResponseEntity
                .status(HttpStatus.OK.value()).build();
    }

}
