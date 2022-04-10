package com.consumer.service.impl;

import com.consumer.client.TaskServiceClient;
import com.consumer.constants.InfoMessages;
import com.consumer.dto.rabbit.TaskMessage;
import com.consumer.dto.response.Task;
import com.consumer.enums.TaskStatus;
import com.consumer.exception.TaskException;
import com.consumer.helpers.Helper;
import com.consumer.service.TaskService;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskServiceClient taskServiceClient;

    @Override
    public void workWithText(TaskMessage taskMessage, Channel channel, Long deliveryTag) throws IOException, TaskException {
        try {
            log.info(InfoMessages.TASK_START_PROCESS, taskMessage.getTaskId());
            //имитируем реальные условия работы с рандомным результатом, задержкой и рандомным эксепшеном
            Task task = taskServiceClient.getTaskData(taskMessage.getTaskId());
            //в боевых условиях могли бы обработать текст из объекта task
            log.info(InfoMessages.TRY_WORK_WITH_TEXT, task.getText());
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

            var errorText = e.getCause().toString();
            log.error(errorText);
            throw new TaskException(errorText);
        }
    }

    @Override
    public void setTaskError(TaskMessage taskMessage, Channel channel, Long deliveryTag) throws IOException, TaskException {
        try {
            log.info(InfoMessages.TASK_DLQ_START_PROCESS, taskMessage.getTaskId());
            //помечаем в базе задание как ошибочное
            taskServiceClient.updateTaskStatus(taskMessage.getTaskId(), TaskStatus.ERROR);
            channel.basicAck(deliveryTag, false);
            log.info(InfoMessages.TASK_DLQ_END_PROCESS_SUCCESS, taskMessage.getTaskId());
        } catch (Exception e) {
            //возвращаем обратно в очередь
            channel.basicNack(deliveryTag, false, true);
            var errorText = e.getCause().toString();
            log.error(errorText);
            throw new TaskException(errorText);
        }
    }
}
