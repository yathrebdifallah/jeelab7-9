// analytics-service/src/main/java/com/example/analytics/kafka/BookEventConsumer.java
package com.example.analytics.kafka;

import com.example.analytics.dto.BookEvent;
import com.example.analytics.service.AnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BookEventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(BookEventConsumer.class);

    @Autowired
    private AnalyticsService analyticsService;

    @KafkaListener(topics = "book-events", groupId = "analytics-group")
    public void consume(BookEvent event) {
        logger.info("Received book event: {}", event);

        switch (event.getEventType()) {
            case CREATED:
                analyticsService.recordBookCreated(event.getBook());
                break;
            case UPDATED:
                analyticsService.recordBookUpdated(event.getBook());
                break;
            case DELETED:
                analyticsService.recordBookDeleted(event.getBook());
                break;
        }
    }
}