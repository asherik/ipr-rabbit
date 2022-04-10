package com.taskservice.service;

import com.taskservice.dto.request.TaskRequest;
import com.taskservice.enums.TaskStatus;
import com.taskservice.model.Task;

import java.util.UUID;

public interface TaskService {

    /**
     * Создать задачу.
     * @param taskRequest {@link TaskRequest}
     */
    void createTask(TaskRequest taskRequest);

    /**
     * Выставить статус обработки задания.
     * @param taskId id задания.
     * @param taskStatus статус
     */
    void setTaskStatus(UUID taskId, TaskStatus taskStatus);

    /**
     * Получить данные по заданию.
     * @param taskId id задания.
     * @return  task
     */
    Task getTaskData(UUID taskId);
}
