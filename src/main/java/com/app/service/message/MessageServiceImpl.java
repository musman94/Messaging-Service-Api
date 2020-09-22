package com.app.service.message;

import com.app.dto.mapper.message.MessageMapper;
import com.app.dto.model.message.MessageDto;
import com.app.exception.AppException;
import com.app.exception.EntityType;
import com.app.exception.ExceptionType;
import com.app.model.message.Message;
import com.app.model.user.User;
import com.app.repository.message.MessageRepository;
import com.app.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.app.exception.EntityType.USER;
import static com.app.exception.ExceptionType.ENTITY_NOT_FOUND;

@Slf4j
@Component
public class MessageServiceImpl implements MessageService {
    private final UserRepository userRepository;

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    @Autowired
    public MessageServiceImpl(UserRepository userRepository, MessageRepository messageRepository, MessageMapper messageMapper) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public void sendMessage(MessageDto messageDTO) {
        User userFrom = getUserIfExists(messageDTO.getFrom());
        User userTo = getUserIfExists(messageDTO.getTo());

        checkUserIfBlocked(userTo, userFrom.getUsername());

        Message message = messageMapper.toMessage(messageDTO);

        log.info("Attempting to send message to " + messageDTO.getTo() + " from " + messageDTO.getFrom());

        messageRepository.save(message);

        log.info("Message to " + messageDTO.getTo() + " sent successfully from " + messageDTO.getFrom());
    }

    @Override
    public List<MessageDto> getMessageHistory(String username) {
        User user = getUserIfExists(username);

        log.info("Attempting to fetch the message history of " + username);

        List<Message> messageList = messageRepository.findAllByFrom(username);
        List<MessageDto> messageHistory = new ArrayList<>();

        log.info("Message history of " + username + " fetched successfully");

        for(Message message : messageList) {
            MessageDto messageDto = MessageDto.builder()
                    .to(message.getTo())
                    .message(message.getMessage())
                    .build();

            messageHistory.add(messageDto);
        }

        return messageHistory;
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    private User getUserIfExists(String username) {
        User user = getUser(username);

        if(user == null) {
            log.info("Message send failed as " + username + "doesn't exist");
            throw exception(USER, ENTITY_NOT_FOUND, username);
        }

        return user;
    }

    private void checkUserIfBlocked(User userTo, String fromUsername) {
        if(userTo.getBlockedUsers() != null && userTo.getBlockedUsers().contains(fromUsername)) {
            log.info("Message send failed as " + fromUsername + " is blocked by" + userTo.getUsername());
            throw exception(USER, ENTITY_NOT_FOUND, userTo.getUsername());
        }
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String args) {
        return AppException.throwException(entityType, exceptionType, args);
    }
}

