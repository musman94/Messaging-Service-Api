package com.app.dto.mapper.user;

import com.app.dto.model.user.UserDto;
import com.app.model.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    public UserDto toUserDtoLoginHistory(User user) {
        List<Object> loginHistory = user.getLoginHistory() == null ? new ArrayList<>() : user.getLoginHistory();

        return UserDto.builder()
                .loginHistory(loginHistory)
                .build();
    }

    public User toUser(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
    }
}
