package com.app;

import com.app.dto.mapper.user.UserMapper;
import com.app.dto.model.user.UserBlockDto;
import com.app.dto.model.user.UserDto;
import com.app.model.user.User;
import com.app.repository.user.UserRepository;
import com.app.service.user.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    @Mock
    UserRepository userRepositoryMock;

    @Mock
    UserMapper userMapperMock;

    @Mock
    PasswordEncoder passwordEncoderMock;

    @InjectMocks
    UserServiceImpl userService;

    User testUser;
    User testUser2;
    UserDto testUserDto;
    UserBlockDto testUserBlockDto;

    @Before
    public void setup() {
        testUser = User.builder()
                .username("testUser")
                .password("password")
                .firstName("test")
                .lastName("user")
                .build();

        testUser2 = User.builder()
                .username("testUser2")
                .password("password")
                .firstName("test2")
                .lastName("user")
                .build();

        testUserDto = UserDto.builder()
                .username("testUser")
                .password("password")
                .firstName("test")
                .lastName("user")
                .build();

        testUserBlockDto = UserBlockDto.builder()
                .username("testUser")
                .toBlockUsername("testUser2")
                .build();
    }

    @Test
    public void testSignUp() {
        when(userRepositoryMock.findByUsername(testUserDto.getUsername())).thenReturn(null);
        when(userMapperMock.toUser(testUserDto)).thenReturn(testUser);
        when(passwordEncoderMock.encode(testUserDto.getPassword())).thenReturn(testUserDto.getPassword());
        when(userRepositoryMock.save(testUser)).thenReturn(testUser);
        when(userMapperMock.toUserDto(testUser)).thenReturn(testUserDto);

        assertEquals(testUserDto, userService.signUp(testUserDto));
    }

    @Test(expected = RuntimeException.class)
    public void testSignUpAlreadyExistingUsername() {
        when(userRepositoryMock.findByUsername(testUserDto.getUsername())).thenReturn(testUser);
        userService.signUp(testUserDto);
    }

    @Test
    public void testBlockUser() {
        when(userRepositoryMock.findByUsername(testUserBlockDto.getUsername())).thenReturn(testUser);
        when(userRepositoryMock.findByUsername(testUserBlockDto.getToBlockUsername())).thenReturn(testUser2);
        when(userRepositoryMock.save(testUser)).thenReturn(null);

        assertDoesNotThrow(() -> userService.blockUser(testUserBlockDto));
    }

    @Test
    public void testUpdateLoginHistory() {
        when(userRepositoryMock.findByUsername(testUserBlockDto.getUsername())).thenReturn(testUser);
        when(userRepositoryMock.save(testUser)).thenReturn(null);

        assertDoesNotThrow(() -> userService.updateLoginHistory("testUser", new Date(), "AUTHORIZATION_SUCCESSFUL"));
    }

    @Test
    public void testGetLoginHistory() {
        when(userRepositoryMock.findByUsername("testUser")).thenReturn(testUser);
        when(userMapperMock.toUserDtoLoginHistory(testUser)).thenReturn(testUserDto);

        assertEquals(testUserDto, userService.getLoginHistory("testUser"));
    }

    @Test(expected = RuntimeException.class)
    public void testGetLoginHistoryWhenUserDoesnotExist() {
        when(userRepositoryMock.findByUsername("testUser")).thenReturn(null);
        userService.getLoginHistory("testUser");
    }
}
