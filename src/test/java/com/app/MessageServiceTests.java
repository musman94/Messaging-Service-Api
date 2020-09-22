package com.app;

import com.app.dto.mapper.message.MessageMapper;
import com.app.dto.model.message.MessageDto;
import com.app.model.message.Message;
import com.app.model.user.User;
import com.app.repository.message.MessageRepository;
import com.app.repository.user.UserRepository;
import com.app.service.message.MessageServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTests {
    @Mock
    MessageRepository messageRepositoryMock;

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    MessageMapper messageMapperMock;

    @InjectMocks
    MessageServiceImpl messageService;

    Message message;
    MessageDto messageDto;

    User testUser;
    User testUser2;

    @Before
    public void setup() {
        message = new Message().builder()
                .from("testUser")
                .to("testUser2")
                .message("testMessage")
                .build();

        messageDto = new MessageDto().builder()
                .from("testUser")
                .to("testUser2")
                .message("testMessage")
                .build();

        testUser = new User().builder()
                .username("testUser")
                .build();

        testUser2 = new User().builder()
                .username("testUser2")
                .blockedUsers(new HashSet<>(Arrays.asList("testUser")))
                .build();
    }

    @Test
    public void testGetMessageHistory() {
        messageDto.setFrom(null);

        List<MessageDto> messageHistory = new ArrayList<>(Arrays.asList(messageDto));

        when(userRepositoryMock.findByUsername("testUser")).thenReturn(new User());
        when(messageRepositoryMock.findAllByFrom("testUser")).thenReturn(new ArrayList<>(Arrays.asList(message)));

        assertEquals(messageHistory, messageService.getMessageHistory("testUser"));
    }

    @Test
    public void testSendMessageTest() {
        when(userRepositoryMock.findByUsername("testUser")).thenReturn(new User());
        when(userRepositoryMock.findByUsername("testUser2")).thenReturn(new User());
        when(messageMapperMock.toMessage(messageDto)).thenReturn(message);
        when(messageRepositoryMock.save(message)).thenReturn(null);
        assertDoesNotThrow(() -> messageService.sendMessage(messageDto));
    }

    @Test(expected = RuntimeException.class)
    public void testSendMessageToNonExistentUser() {
        when(userRepositoryMock.findByUsername("testUser")).thenReturn(new User());
        when(userRepositoryMock.findByUsername("testUser2")).thenReturn(null);

        messageService.sendMessage(messageDto);
    }

    @Test(expected = RuntimeException.class)
    public void testSendMessageFromBlockedUser() {
        when(userRepositoryMock.findByUsername("testUser")).thenReturn(testUser);
        when(userRepositoryMock.findByUsername("testUser2")).thenReturn(testUser2);

        messageService.sendMessage(messageDto);
    }
}
