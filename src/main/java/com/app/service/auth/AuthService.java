package com.app.service.auth;

import com.app.dto.mapper.user.UserMapper;
import com.app.dto.model.user.UserDto;
import com.app.exception.AppException;
import com.app.exception.EntityType;
import com.app.exception.ExceptionType;
import com.app.model.user.User;
import com.app.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.app.exception.EntityType.USER;
import static com.app.exception.ExceptionType.ENTITY_NOT_FOUND;

@Slf4j
@Component
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);

        if(user == null) {
            throw new UsernameNotFoundException("No user found for "+ username + ".");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>());
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
