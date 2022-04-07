package com.producer.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TaskRequest {
    @NotBlank
    private String text;
}
