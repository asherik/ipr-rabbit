package com.producer.sender.impl;

import com.producer.constants.InfoMessages;
import com.producer.dto.rabbit.TaskMessage;
import com.producer.enums.TaskStatus;
import com.producer.mapper.TaskMapper;
import com.producer.model.Task;
import com.producer.repository.TaskRepository;
import com.producer.sender.TaskSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class TaskSenderImpl implements TaskSender {

    private final RabbitTemplate rabbitTemplate;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Value("${ipr-rabbit.taskQueue}")
    private String taskQueue;

    public TaskSenderImpl(RabbitTemplate rabbitTemplate, TaskRepository taskRepository, TaskMapper taskMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public void sendTask(TaskMessage taskMessage) {
        log.info(InfoMessages.PRODUCER_SEND_MESSAGE_START);
        rabbitTemplate.convertAndSend(
                taskQueue,
                taskMessage
        );
        log.info(InfoMessages.PRODUCER_SEND_MESSAGE_END);
    }

    @Override
    public void handleTask() {
        log.info(InfoMessages.CHECKING_FREE_TASK);
        //запрашиваем из базы свободное самое старое задание
        Task task = taskRepository.findFirstByStatusOrderByDateCreateAsc(TaskStatus.NEW_TASK.toString());
        if (Objects.nonNull(task)) {
            log.info(InfoMessages.FREE_TASK_FOUND);
            sendTask(taskMapper.toTaskMessage(task));
            task.setStatus(TaskStatus.WAIT_WORK.toString());
            taskRepository.save(task);
        }
    }
}
