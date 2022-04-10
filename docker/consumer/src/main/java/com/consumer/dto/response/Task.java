package com.consumer.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Dto для заданий.
 */
@Data
@NoArgsConstructor
public class Task implements Serializable {
    private UUID id;
    private String text;
    private String status;
    private LocalDateTime dateCreate;
    private LocalDateTime dateProcessing;
}
