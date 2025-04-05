package com.jumbochips.poml_jpa.common.websocket.service;

import com.jumbochips.poml_jpa.common.websocket.entity.ChatMessage;
import com.jumbochips.poml_jpa.common.websocket.handler.AlertHandler;
import com.jumbochips.poml_jpa.common.websocket.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final AlertHandler alertHandler;

    public void save(ChatMessage message) {
        chatMessageRepository.save(message);

        // 관리자 아닌 사용자의 메시지일 때만 알림
        if (!"관리자".equals(message.getSender())) {
            alertHandler.sendAlert("💬 " + message.getSender() + "님의 새로운 채팅 메시지");
        }
    }

    public List<ChatMessage> getMessagesByRoom(String roomId) {
        return chatMessageRepository.findByRoomIdOrderBySentAtAsc(roomId);
    }
}
