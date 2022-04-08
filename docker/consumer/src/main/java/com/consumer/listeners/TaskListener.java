package com.consumer.listeners;

import com.consumer.client.TaskServiceClient;
import com.consumer.constants.InfoMessages;
import com.consumer.dto.rabbit.TaskMessage;
import com.consumer.enums.TaskStatus;
import com.consumer.exception.TaskException;
import com.consumer.helpers.Helper;
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

    private final TaskServiceClient taskServiceClient;

    /**
     * Process income task message.
     *
     * @param taskMessage taskMessage
     * @param channel     channel
     * @param deliveryTag deliveryTag
     */
    @RabbitListener(queues = "ipr.task", containerFactory = "rabbitListenerContainerFactory")
    public void processTask(TaskMessage taskMessage,
                            Channel channel,
                            @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws Exception {
        try {
            log.info(InfoMessages.TASK_START_PROCESS, taskMessage.getTaskId());
            //имитируем реальные условия работы с рандомным результатом, задержкой и рандомным эксепшеном
            if (Helper.getImitateWorkRandomResult()) {
                channel.basicAck(deliveryTag, false);
                log.info(InfoMessages.TASK_END_PROCESS_SUCCESS, taskMessage.getTaskId());
                taskServiceClient.updateTaskStatus(taskMessage.getTaskId(), TaskStatus.SUCCESS);
            } else {
                //возвращаем обратно в очередь
                channel.basicNack(deliveryTag, false, true);
                log.info(InfoMessages.TASK_END_PROCESS_NO_SUCCESS, taskMessage.getTaskId());
            }
        } catch (Exception e) {
            //отправим в dlq
            channel.basicNack(deliveryTag, false, false);

            var errorText = acquireErrorMessage(e);
            log.error(errorText);
            throw new TaskException(errorText);
        }
    }


    /**
     * Process income dlq task message.
     *
     * @param taskMessage taskMessage
     * @param channel     channel
     * @param deliveryTag deliveryTag
     */
    @RabbitListener(queues = "${ipr-rabbit.dlqQueue}", containerFactory = "rabbitListenerContainerFactory")
    public void processDlqTask(TaskMessage taskMessage,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws Exception {
        try {
            log.info(InfoMessages.TASK_DLQ_START_PROCESS, taskMessage.getTaskId());
            //помечаем в базе задание как ошибочное
            taskServiceClient.updateTaskStatus(taskMessage.getTaskId(), TaskStatus.ERROR);
            channel.basicAck(deliveryTag, false);
            log.info(InfoMessages.TASK_DLQ_END_PROCESS_SUCCESS, taskMessage.getTaskId());
        } catch (Exception e) {
            //возвращаем обратно в очередь
            channel.basicNack(deliveryTag, false, true);
            var errorText = acquireErrorMessage(e);
            log.error(errorText);
            throw new TaskException(errorText);
        }
    }

    /**
     * Вытаскиваем сообщение из ошибки.
     *
     * @param e ошибка
     * @return String
     */
    private String acquireErrorMessage(Exception e) {
        return e.getCause().toString();
    }
}
