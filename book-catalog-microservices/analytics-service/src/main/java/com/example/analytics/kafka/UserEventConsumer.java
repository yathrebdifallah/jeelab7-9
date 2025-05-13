// analytics-service/src/main/java/com/example/analytics/kafka/UserEventConsumer.java
package com.example.analytics.kafka;

import com.example.analytics.dto.UserEvent;
import com.example.analytics.service.AnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(UserEventConsumer.class);

    @Autowired
    private AnalyticsService analyticsService;

    @KafkaListener(topics = "user-events", groupId = "analytics-group")
    public void consume(UserEvent event) {
        logger.info("Received user event: {}", event);

        switch (event.getEventType()) {
            case REGISTERED:
                analyticsService.recordUserRegistered(event.getUser());
                break;
            case UPDATED:
                analyticsService.recordUserUpdated(event.getUser());
                break;
            case DELETED:
                analyticsService.recordUserDeleted(event.getUser());
                break;
        }
    }
}