// book-service/src/main/java/com/example/book/dto/BookEvent.java
package com.example.book.dto;

import com.example.book.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookEvent {
    public enum EventType {
        CREATED, UPDATED, DELETED
    }

    private EventType eventType;
    private Book book;
}