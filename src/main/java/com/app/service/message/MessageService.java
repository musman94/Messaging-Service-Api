package com.app.service.message;

import com.app.dto.model.message.MessageDto;

import java.util.List;

public interface MessageService {
    void sendMessage(MessageDto messageDTO);
    List<MessageDto> getMessageHistory(String username);
}
