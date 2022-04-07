package com.producer.repository;


import com.producer.enums.TaskStatus;
import com.producer.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с {@link Task}.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    Task findFirstByStatusOrderByDateCreateAsc(String taskStatus);
}
