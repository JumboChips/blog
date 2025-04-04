package com.jumbochips.poml_jpa.common.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatMessage {
    private String sender;
    private String message;
    private String sentAt;
}
