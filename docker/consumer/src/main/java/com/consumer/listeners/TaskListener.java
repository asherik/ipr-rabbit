package com.consumer.listeners;

import com.consumer.dto.rabbit.TaskMessage;
import com.consumer.service.TaskService;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class TaskListener {


    private final TaskService taskService;

    /**
     * Обработка сообщений из основной очереди.
     *
     * @param taskMessage taskMessage
     * @param channel     channel
     * @param deliveryTag deliveryTag
     */
    @RabbitListener(queues = "${ipr-rabbit.taskQueue}", containerFactory = "rabbitListenerContainerFactory")
    public void processTask(TaskMessage taskMessage,
                            Channel channel,
                            @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws Exception {
        taskService.workWithText(taskMessage, channel, deliveryTag);
    }

    /**
     * Обработка сообщений из dlq очереди.
     *
     * @param taskMessage taskMessage
     * @param channel     channel
     * @param deliveryTag deliveryTag
     */
    @RabbitListener(queues = "${ipr-rabbit.dlqQueue}", containerFactory = "rabbitListenerContainerFactory")
    public void processDlqTask(TaskMessage taskMessage,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws Exception {
        taskService.setTaskError(taskMessage, channel, deliveryTag);
    }

}
