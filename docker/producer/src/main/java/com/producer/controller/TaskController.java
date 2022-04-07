package com.producer.controller;

import com.producer.constants.ApiConstants;
import com.producer.dto.request.TaskRequest;
import com.producer.service.TaskService;
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
@RequestMapping(ApiConstants.V1)
public class TaskController {
    private final TaskService taskService;

    @PostMapping(value = ApiConstants.CREATE_TASK, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTask(@RequestBody @Valid @NotNull TaskRequest taskRequest) {
        taskService.createTask(taskRequest);
        return ResponseEntity
                .status(HttpStatus.OK.value()).build();
    }
}
