package com.producer.sender.impl;

import com.producer.constants.InfoMessages;
import com.producer.dto.rabbit.TaskMessage;
import com.producer.sender.TaskSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class TaskSenderImpl implements TaskSender {

    private final RabbitTemplate rabbitTemplate;

    @Value("${ipr-rabbit.taskQueue}")
    private String taskQueue;

    public TaskSenderImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
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
}
