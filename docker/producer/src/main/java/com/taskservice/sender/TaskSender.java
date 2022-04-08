package com.taskservice.sender;

import com.taskservice.dto.rabbit.TaskMessage;

public interface TaskSender {

    /**
     * Отправка задания в очередь.
     *
     * @param taskMessage
     */
    void sendTask(TaskMessage taskMessage);

    /**
     * Поиск задания и отправка в реббит
     */
    void handleTask();

}
