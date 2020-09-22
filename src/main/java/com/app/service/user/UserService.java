package com.app.service.user;
import com.app.dto.model.user.UserBlockDto;
import com.app.dto.model.user.UserDto;

import java.util.Date;

public interface UserService {
    UserDto signUp(UserDto userDto);

    void blockUser(UserBlockDto userBlockDto);

    UserDto getLoginHistory(String username);

    void updateLoginHistory(String username, Date date, String status);
}
