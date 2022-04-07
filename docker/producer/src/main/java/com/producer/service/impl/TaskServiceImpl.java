package com.producer.service.impl;

import com.producer.dto.request.TaskRequest;
import com.producer.mapper.TaskMapper;
import com.producer.repository.TaskRepository;
import com.producer.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public void createTask(TaskRequest taskRequest) {
        taskRepository.save(taskMapper.toTask(taskRequest));
    }
}
