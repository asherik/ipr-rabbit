package com.consumer.service;

import com.consumer.dto.rabbit.TaskMessage;
import com.consumer.exception.TaskException;
import com.rabbitmq.client.Channel;

import java.io.IOException;

public interface TaskService {

    /**
     * Обработка задания.
     *
     * @param taskMessage Сообщение
     * @param channel     Шлюз
     * @param deliveryTag Тег доставки
     */
    void workWithText(TaskMessage taskMessage, Channel channel, Long deliveryTag) throws IOException, TaskException;

    /**
     * Измененить статус задания на ошибочное.
     *
     * @param taskMessage Сообщение
     * @param channel     Шлюз
     * @param deliveryTag Тег доставки
     */
    void setTaskError(TaskMessage taskMessage, Channel channel, Long deliveryTag) throws IOException, TaskException;
}
