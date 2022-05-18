package com.taskservice.service.impl;

import com.taskservice.dto.request.UserRegRequest;
import com.taskservice.exception.UserLoginAlreadyExistException;
import com.taskservice.mapper.UserMapper;
import com.taskservice.model.User;
import com.taskservice.repository.UserRepository;
import com.taskservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void createUser(UserRegRequest userRegRequest) throws UserLoginAlreadyExistException {

        User user = userRepository.findByLogin(userRegRequest.getLogin());
        if (Objects.nonNull(user)) {
            throw new UserLoginAlreadyExistException(userRegRequest.getLogin());
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        var passwordEncode = encoder.encode(userRegRequest.getPassword());
        userRepository.save(userMapper.toUser(userRegRequest, passwordEncode));
    }

}
