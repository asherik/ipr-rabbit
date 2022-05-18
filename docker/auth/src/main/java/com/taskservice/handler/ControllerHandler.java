package com.taskservice.handler;

import com.taskservice.exception.UserLoginAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exceptions handlers for controller.
 */
@Slf4j
@RestControllerAdvice
public class ControllerHandler {
    @ExceptionHandler({UserLoginAlreadyExistException.class})
    public ResponseEntity<String> userLoginAlreadyExistError(UserLoginAlreadyExistException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
