// user-service/src/main/java/com/example/user/dto/UserEvent.java
package com.example.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
    public enum EventType {
        REGISTERED, UPDATED, DELETED
    }

    private EventType eventType;
    private UserDto user;
}