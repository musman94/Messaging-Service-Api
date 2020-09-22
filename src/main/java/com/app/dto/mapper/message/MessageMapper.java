package com.app.dto.mapper.message;

import com.app.dto.model.message.MessageDto;
import com.app.model.message.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public MessageDto toMessageDto(Message message) {
        return MessageDto.builder()
                .from(message.getFrom())
                .to(message.getTo())
                .message(message.getMessage())
                .build();
    }

    public Message toMessage(MessageDto messageDTO) {
        return Message.builder()
                .from(messageDTO.getFrom())
                .to(messageDTO.getTo())
                .message(messageDTO.getMessage())
                .build();
    }
}
