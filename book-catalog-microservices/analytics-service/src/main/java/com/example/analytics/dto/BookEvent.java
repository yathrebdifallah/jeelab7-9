// analytics-service/src/main/java/com/example/analytics/dto/BookEvent.java
package com.example.analytics.dto;

import lombok.Data;

@Data
public class BookEvent {
    public enum EventType {
        CREATED, UPDATED, DELETED
    }

    private EventType eventType;
    private Book book;
}