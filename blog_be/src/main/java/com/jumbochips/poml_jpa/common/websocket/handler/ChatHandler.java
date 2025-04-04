package com.jumbochips.poml_jpa.common.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumbochips.poml_jpa.common.websocket.dto.ChatMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatHandler extends TextWebSocketHandler {

    // 세션 ID → 세션 정보 저장
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // 방 ID → 세션 리스트
    private static final Map<String, Set<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = getRoomIdFromQuery(session);
        roomSessions.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(session);
        sessions.put(session.getId(), session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = getRoomIdFromQuery(session);
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

        for (WebSocketSession s : roomSessions.getOrDefault(roomId, Set.of())) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
        roomSessions.values().forEach(set -> set.remove(session));
    }

    private String getRoomIdFromQuery(WebSocketSession session) {
        // 예: ws://localhost:8080/ws/chat?roomId=123
        String query = session.getUri().getQuery();
        if (query != null && query.startsWith("roomId=")) {
            return query.substring("roomId=".length());
        }
        return "default";
    }
}
