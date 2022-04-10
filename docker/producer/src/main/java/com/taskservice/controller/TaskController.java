package com.taskservice.controller;

import com.taskservice.constants.ApiConstants;
import com.taskservice.dto.request.TaskRequest;
import com.taskservice.enums.TaskStatus;
import com.taskservice.model.Task;
import com.taskservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.V1)
public class TaskController {
    private final TaskService taskService;

    @PostMapping(value = ApiConstants.CREATE_TASK, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTask(@RequestBody @Valid @NotNull TaskRequest taskRequest) {
        taskService.createTask(taskRequest);
        return ResponseEntity
                .status(HttpStatus.OK.value()).build();
    }

    @PutMapping(value = ApiConstants.UPDATE_TASK_STATUS)
    public ResponseEntity<Void> updateTaskStatus(@RequestParam @Valid @NotNull UUID taskId,
                                                 @RequestParam @Valid @NotNull TaskStatus taskStatus) {
        taskService.setTaskStatus(taskId, taskStatus);
        return ResponseEntity
                .status(HttpStatus.OK.value()).build();
    }

    @GetMapping(value = ApiConstants.GET_TASK_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> getTaskData(@RequestParam @Valid @NotNull UUID taskId) {
        return ResponseEntity
                .status(HttpStatus.OK).body(taskService.getTaskData(taskId));
    }
}
