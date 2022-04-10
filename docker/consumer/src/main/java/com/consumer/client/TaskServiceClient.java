package com.consumer.client;

import com.consumer.config.TaskServiceFeignConfiguration;
import com.consumer.constants.ApiConstants;
import com.consumer.dto.response.Task;
import com.consumer.enums.TaskStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Клиент сервиса "Task Service".
 */
@FeignClient(name = "task-service-client", url = "${feign.services.task-service.url:}", path = "${feign.services.task-service.path:}",
        configuration = TaskServiceFeignConfiguration.class)
public interface TaskServiceClient {

    @PutMapping(value = ApiConstants.UPDATE_TASK_STATUS)
    void updateTaskStatus(@RequestParam @Valid @NotNull UUID taskId,
                          @RequestParam @Valid @NotNull TaskStatus taskStatus);

    @GetMapping(value = ApiConstants.GET_TASK_DATA, consumes = MediaType.APPLICATION_JSON_VALUE)
    Task getTaskData(@RequestParam @Valid @NotNull UUID taskId);
}
