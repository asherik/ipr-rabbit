package com.producer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Модель для заданий.
 */
@Table(name = "task")
@Entity
@NoArgsConstructor
public class Task implements Serializable {

    private static final long serialVersionUID = -3576152568443724782L;
    @Id
    @GeneratedValue
    @Getter
    @Column(name = "id")
    private UUID id;

    @Column(name = "text", nullable = false)
    @Getter
    @Setter
    private String text;

    @Column(name = "status", nullable = false)
    @Getter
    @Setter
    private String status;

    @Column(name = "date_create", nullable = false)
    @Getter
    @Setter
    private LocalDateTime dateCreate;

    @Column(name = "date_processing")
    @Getter
    @Setter
    private LocalDateTime dateProcessing;
}
