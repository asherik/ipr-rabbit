package com.producer.sender;

import com.producer.dto.rabbit.TaskMessage;
import com.producer.helpers.Helper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class Submitter {

    private TaskSender taskSender;

    private void tasker() {
        while (true) {
            handleTask();
            //спим 5 секунд
            Helper.sleep(5000);
        }
    }

    private void handleTask() {
        //запрашиваем из базы свободное самое старое задание
        //валидируем на заполненность всех полей из базы
        if (validateTaskFields()) {
            taskSender.sendTask(new TaskMessage());
            //отправляем в реббит
            //ставим статус задания в базе как "ожидает обработки"
        } else {
            //лог о том что в полях задания есть ошибки и печатаем поля задания
        }
    }

    /**
     * Валидация полей задания.
     * @return boolean
     */
    private boolean validateTaskFields(){
        return false;
    }

}
