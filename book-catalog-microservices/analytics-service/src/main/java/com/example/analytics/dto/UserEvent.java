// analytics-service/src/main/java/com/example/analytics/dto/UserEvent.java
package com.example.analytics.dto;

import lombok.Data;

@Data
public class UserEvent {
    public enum EventType {
        REGISTERED, UPDATED, DELETED
    }

    private EventType eventType;
    private UserDto user;
}