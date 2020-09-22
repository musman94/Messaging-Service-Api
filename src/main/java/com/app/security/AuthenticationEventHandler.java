package com.app.security;

import com.app.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class AuthenticationEventHandler {
    private final UserService userService;

    @Autowired
    public AuthenticationEventHandler(UserService userService) {
        this.userService = userService;
    }

    @EventListener({AuthenticationSuccessEvent.class, InteractiveAuthenticationSuccessEvent.class})
    public void processAuthenticationSuccessEvent(AbstractAuthenticationEvent e) {
        String username = ((UserDetails) e.getAuthentication().getPrincipal()).getUsername();

        log.info(username + " successfully logged in, saving the login attempt into the login history");

        userService.updateLoginHistory(username, new Date(), "AUTHENTICATION_SUCCESSFUL");
    }

    @EventListener({AbstractAuthenticationFailureEvent.class})
    public void processAuthenticationFailEvent(AbstractAuthenticationFailureEvent e) {
        String username = e.getAuthentication().getPrincipal().toString();

        log.info(username + " failed to login, saving the login attempt into the login history");

        userService.updateLoginHistory(username, new Date(), "AUTHENTICATION_FAILED");
    }
}

