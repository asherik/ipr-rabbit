package com.taskservice.repository;


import com.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с {@link Task}.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    Task findFirstByStatusOrderByDateCreateAsc(String taskStatus);
}
