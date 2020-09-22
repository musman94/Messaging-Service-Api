package com.app.service.user;

import com.app.dto.mapper.user.UserMapper;
import com.app.dto.model.user.UserBlockDto;
import com.app.dto.model.user.UserDto;
import com.app.exception.AppException;
import com.app.exception.EntityType;
import com.app.exception.ExceptionType;
import com.app.model.user.User;
import com.app.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.app.exception.EntityType.USER;
import static com.app.exception.ExceptionType.ENTITY_ALREADY_EXISTS;
import static com.app.exception.ExceptionType.ENTITY_NOT_FOUND;

@Slf4j
@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto signUp(UserDto userDto) {
        User user = getUser(userDto.getUsername());

        if(user == null) {
            user = userMapper.toUser(userDto);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            return userMapper.toUserDto(userRepository.save(user));
        }

        log.info("Signup failed, the username: " + userDto.getUsername() + " already exists");
        throw exception(USER, ENTITY_ALREADY_EXISTS, userDto.getUsername());
    }

    @Override
    public void blockUser(UserBlockDto userBlockDto) {
        User user = getUserIfExists(userBlockDto.getUsername());
        User toBlockUser = getUserIfExists(userBlockDto.getToBlockUsername());

        if(user.getBlockedUsers() == null) {
            user.setBlockedUsers(new HashSet<>());
        }

        user.getBlockedUsers().add(userBlockDto.getToBlockUsername());

        log.info("Attempting to block " + userBlockDto.getToBlockUsername() + "for " + userBlockDto.getUsername());

        userRepository.save(user);

        log.info(userBlockDto.getToBlockUsername() + "blocked successfully by " + userBlockDto.getUsername());
    }

    @Override
    public UserDto getLoginHistory(String username) {
        log.info("Fetching the login history for " + username);

        User user = getUserIfExists(username);

        log.info("Login history fetched successfully");

        return userMapper.toUserDtoLoginHistory(user);
    }

    @Override
    public void updateLoginHistory(String username, Date date, String status) {
        User user = getUserIfExists(username);

        Map<String, Object> loginAttempt = new HashMap<>();

        loginAttempt.put("Date", date);
        loginAttempt.put("Status", status);

        List<Object> loginHistory = user.getLoginHistory();

        if(loginHistory == null) {
            loginHistory = new ArrayList<>();
        }

        loginHistory.add(loginAttempt);
        user.setLoginHistory(loginHistory);

        log.info("Saving login attempt into " + username + "'s login history");

        userRepository.save(user);

        log.info("Login attempt saved successfully");
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    private User getUserIfExists(String username) {
        User user = getUser(username);

        if(user == null) {
            log.info("Couldn't find the user with username: " + username + ". Blocking request failed");
            throw exception(USER, ENTITY_NOT_FOUND, username);
        }

        return user;
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String id) {
        return AppException.throwException(entityType, exceptionType, id);
    }
}
