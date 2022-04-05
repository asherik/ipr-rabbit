package com.producer.sender;

import com.producer.dto.rabbit.TaskMessage;

public interface TaskSender {

    /**
     * Отправка задания в очередь.
     *
     * @param taskMessage
     */
    void sendTask(TaskMessage taskMessage);
}
