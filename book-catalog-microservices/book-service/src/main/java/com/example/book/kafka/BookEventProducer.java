// book-service/src/main/java/com/example/book/kafka/BookEventProducer.java
package com.example.book.kafka;

import com.example.book.dto.BookEvent;
import com.example.book.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookEventProducer {
    private static final String TOPIC = "book-events";

    @Autowired
    private KafkaTemplate<String, BookEvent> kafkaTemplate;

    public void sendBookCreatedEvent(Book book) {
        BookEvent event = new BookEvent(BookEvent.EventType.CREATED, book);
        kafkaTemplate.send(TOPIC, event);
    }

    public void sendBookUpdatedEvent(Book book) {
        BookEvent event = new BookEvent(BookEvent.EventType.UPDATED, book);
        kafkaTemplate.send(TOPIC, event);
    }

    public void sendBookDeletedEvent(Book book) {
        BookEvent event = new BookEvent(BookEvent.EventType.DELETED, book);
        kafkaTemplate.send(TOPIC, event);
    }
}