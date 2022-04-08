package com.taskservice.service;

import com.taskservice.dto.request.TaskRequest;
import com.taskservice.enums.TaskStatus;

import java.util.UUID;

public interface TaskService {

    /**
     * Создать задачу.
     */
    void createTask(TaskRequest taskRequest);

    /**
     * Выставить статус обработки задания.
     */
    void setTaskStatus(UUID taskId, TaskStatus taskStatus);
}
