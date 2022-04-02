package com.consumer.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * Exceptions handlers for rabbit.
 */
@Slf4j
@Component
public class RabbitHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable e) {
        log.error(e.getCause().getMessage());
    }
}
