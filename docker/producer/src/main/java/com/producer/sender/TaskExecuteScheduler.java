package com.producer.sender;

import com.producer.constants.InfoMessages;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@AllArgsConstructor
@Slf4j
public class TaskExecuteScheduler {

    private final TaskSender taskSender;

    @Scheduled(fixedDelay = 5000)
    private void taskProduceTreadRun() {
        taskSender.handleTask();
    }
}
