package com.producer.mapper;

import com.producer.config.MapStructConfig;
import com.producer.dto.rabbit.TaskMessage;
import com.producer.dto.request.TaskRequest;
import com.producer.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface TaskMapper {

    /**
     * Преобразовать в объект {@link Task}.
     *
     * @param taskRequest объект {@link TaskRequest}
     * @return объект {@link Task}
     */
    @Mapping(target = "status", expression = "java(com.producer.enums.TaskStatus.NEW_TASK.toString())")
    @Mapping(target = "dateCreate", expression = "java(java.time.LocalDateTime.now())")
    Task toTask(TaskRequest taskRequest);

    /**
     * Преобразовать в объект {@link TaskMessage}.
     *
     * @param task объект {@link Task}
     * @return объект {@link TaskMessage}
     */
    @Mapping(target = "messageId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "taskId", source = "id")
    TaskMessage toTaskMessage(Task task);

}