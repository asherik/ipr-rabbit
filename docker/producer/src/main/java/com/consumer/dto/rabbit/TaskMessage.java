package com.consumer.dto.rabbit;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class TaskMessage {
    private UUID messageId;
    private UUID taskId;
}
