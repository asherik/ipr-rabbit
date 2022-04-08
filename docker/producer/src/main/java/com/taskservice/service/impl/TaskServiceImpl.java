package com.taskservice.service.impl;

import com.taskservice.dto.request.TaskRequest;
import com.taskservice.enums.TaskStatus;
import com.taskservice.mapper.TaskMapper;
import com.taskservice.model.Task;
import com.taskservice.repository.TaskRepository;
import com.taskservice.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public void createTask(TaskRequest taskRequest) {
        taskRepository.save(taskMapper.toTask(taskRequest));
    }

    @Override
    public void setTaskStatus(UUID taskId, TaskStatus taskStatus) {
        //TODO сделать обработку эксепшена при не найденном id задания
        Task task = taskRepository.findById(taskId).orElseThrow();
        task.setStatus(taskStatus.toString());
        taskRepository.save(task);
    }
}
