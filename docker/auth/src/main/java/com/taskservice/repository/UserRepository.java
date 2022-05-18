package com.taskservice.repository;


import com.taskservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с {@link User}.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByLogin(String login);

}
