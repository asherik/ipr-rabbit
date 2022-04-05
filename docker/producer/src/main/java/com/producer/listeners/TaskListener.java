package com.producer.listeners;

import com.producer.constants.ErrorMessages;
import com.producer.constants.InfoMessages;
import com.producer.dto.rabbit.TaskMessage;
import com.producer.exception.TaskException;
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
     * Process income dlq task message.
     *
     * @param taskMessage taskMessage
     * @param channel     channel
     * @param deliveryTag deliveryTag
     */
    @RabbitListener(queues = "${ipr-rabbit.dlqQueue}", containerFactory = "rabbitListenerContainerFactory")
    public void processTask(TaskMessage taskMessage,
                            Channel channel,
                            @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag) throws Exception {
        try {
            log.debug(InfoMessages.TASK_START_PROCESS, taskMessage.getTaskId());
            //помечаем в базе задание как ошибочное
            channel.basicAck(deliveryTag, false);
            log.debug(InfoMessages.TASK_END_PROCESS_SUCCESS, taskMessage.getTaskId());
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
