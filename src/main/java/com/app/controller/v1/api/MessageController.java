package com.app.controller.v1.api;

import com.app.controller.v1.request.SendMessageRequest;
import com.app.dto.model.message.MessageDto;
import com.app.dto.response.Response;
import com.app.dto.response.Status;
import com.app.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/sendMessage")
    public Response sendMessage(@RequestBody @Validated SendMessageRequest sendMessageRequest) {
        sendMessageToUser(sendMessageRequest);

        return Response.builder()
                .status(Status.OK)
                .message("Message sent")
                .build();
    }

    @GetMapping("/getMessageHistory")
    public Response getMessageHistory(@RequestParam @Validated String username) {
        List<MessageDto> messageHistory = messageService.getMessageHistory(username);

        return Response.builder()
                .status(Status.OK)
                .payload(messageHistory)
                .build();
    }

    private void sendMessageToUser(SendMessageRequest sendMessageRequest) {
        MessageDto messageDTO = MessageDto.builder()
                .from(sendMessageRequest.getFrom())
                .to(sendMessageRequest.getTo())
                .message(sendMessageRequest.getMessage())
                .build();

        messageService.sendMessage(messageDTO);
    }

}
