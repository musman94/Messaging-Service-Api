package com.app.controller.v1.api;

import com.app.controller.v1.request.UserBlockRequest;
import com.app.dto.model.user.UserBlockDto;
import com.app.dto.model.user.UserDto;
import com.app.dto.response.Response;
import com.app.dto.response.Status;
import com.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/block")
    public Response block(@RequestBody @Validated UserBlockRequest userBlockRequest) {
        blockUser(userBlockRequest);

        return Response.builder()
                .status(Status.OK)
                .message("User blocked")
                .build();
    }

    @GetMapping("/getLoginHistory")
    public Response getLoginHistory(@RequestParam @Validated String username) {
        UserDto loginHistory = userService.getLoginHistory(username);

        return Response.builder()
                .status(Status.OK)
                .payload(loginHistory)
                .build();
    }

    private void blockUser(UserBlockRequest userBlockRequest) {
        UserBlockDto userBlockDTO = UserBlockDto.builder()
                .username(userBlockRequest.getUsername())
                .toBlockUsername(userBlockRequest.getToBlockUsername())
                .build();

        userService.blockUser(userBlockDTO);

    }

}
