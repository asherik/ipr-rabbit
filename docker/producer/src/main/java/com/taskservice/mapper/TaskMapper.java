package com.taskservice.mapper;

import com.taskservice.config.MapStructConfig;
import com.taskservice.dto.rabbit.TaskMessage;
import com.taskservice.dto.request.TaskRequest;
import com.taskservice.model.Task;
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
    @Mapping(target = "status", expression = "java(com.taskservice.enums.TaskStatus.NEW_TASK.toString())")
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