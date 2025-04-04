package com.jumbochips.poml_jpa.common.websocket.controller;

import com.jumbochips.poml_jpa.common.websocket.entity.ChatMessage;
import com.jumbochips.poml_jpa.common.websocket.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/messages")
    public List<ChatMessage> getMessages(@RequestParam String roomId) {
        return chatMessageService.getMessagesByRoom(roomId);
    }
}
