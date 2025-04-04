package com.jumbochips.poml_jpa.common.websocket.service;

import com.jumbochips.poml_jpa.common.websocket.entity.ChatMessage;
import com.jumbochips.poml_jpa.common.websocket.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public void save(ChatMessage message) {
        chatMessageRepository.save(message);
    }

    public List<ChatMessage> getMessagesByRoom(String roomId) {
        return chatMessageRepository.findByRoomIdOrderBySentAtAsc(roomId);
    }
}
