package com.taskservice.mapper;

import com.taskservice.config.MapStructConfig;
import com.taskservice.dto.request.UserRegRequest;
import com.taskservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface UserMapper {

    /**
     * Преобразовать в объект {@link User}.
     *
     * @param userRegRequest объект {@link UserRegRequest}
     * @param passwordEncode зашифрованный пароль
     * @return объект {@link User}
     */
    @Mapping(target = "dateCreate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "password", source = "passwordEncode")
    User toUser(UserRegRequest userRegRequest, String passwordEncode);

}
