package com.producer.service;

import com.producer.dto.request.TaskRequest;

public interface TaskService {

    /**
     * Создать задачу.
     */
    void createTask(TaskRequest taskRequest);

}
