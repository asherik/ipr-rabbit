package com.taskservice.model;

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
@Table(name = "user")
@Entity
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -3576152568443724782L;

    @Id
    @GeneratedValue
    @Getter
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    @Getter
    @Setter
    private String name;

    @Column(name = "lastname", nullable = false)
    @Getter
    @Setter
    private String lastname;

    @Column(name = "middlename", nullable = false)
    @Getter
    @Setter
    private String middlename;

    @Column(name = "login", nullable = false)
    @Getter
    @Setter
    private String login;

    @Column(name = "password", nullable = false)
    @Getter
    @Setter
    private String password;

    @Column(name = "date_create")
    @Getter
    @Setter
    private LocalDateTime dateCreate;
}
