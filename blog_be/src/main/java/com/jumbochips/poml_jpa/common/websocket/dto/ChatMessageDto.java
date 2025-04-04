package com.jumbochips.poml_jpa.common.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    private String sender;
    private String message;
    private String sentAt;
}
