package com.consumer.listeners;

import com.consumer.constants.ErrorMessages;
import com.consumer.dto.rabbit.TaskMessage;
import com.consumer.exception.TaskException;
import com.consumer.helpers.Helper;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class TaskListener {

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
            log.debug(ErrorMessages.TASK_START_PROCESS, taskMessage.getTaskId());
            //имитируем реальные условия работы с рандомным результатом, задержкой и рандомным эксепшеном
            Helper.getImitateWorkRandomResult();
            channel.basicAck(deliveryTag, false);
            log.debug(ErrorMessages.TASK_END_PROCESS_SUCCESS, taskMessage.getTaskId());
        } catch (Exception e) {
            if (Objects.nonNull(taskMessage.getTaskId())) {
                //возвращаем обратно в очередь
                channel.basicNack(deliveryTag, false, true);
            } else {
                //отправим в dlq
                channel.basicNack(deliveryTag, false, false);
            }

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
        String errorText;
        if ((e instanceof DataIntegrityViolationException) && (e.getCause() instanceof ConstraintViolationException)) {
            log.error("Error while writing to DB ", e);
            var error = (ConstraintViolationException) e.getCause();
            if (Objects.nonNull(error.getSQLException())) {
                errorText = error.getSQLException().toString();
            } else {
                errorText = error.toString();
            }
        } else {
            errorText = e.getCause().toString();
        }
        return errorText;
    }
}
