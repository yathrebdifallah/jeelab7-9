// user-service/src/main/java/com/example/user/kafka/UserEventProducer.java
package com.example.user.kafka;

import com.example.user.dto.UserDto;
import com.example.user.dto.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {
    private static final String TOPIC = "user-events";

    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    public void sendUserRegisteredEvent(UserDto user) {
        UserEvent event = new UserEvent(UserEvent.EventType.REGISTERED, user);
        kafkaTemplate.send(TOPIC, event);
    }

    public void sendUserUpdatedEvent(UserDto user) {
        UserEvent event = new UserEvent(UserEvent.EventType.UPDATED, user);
        kafkaTemplate.send(TOPIC, event);
    }

    public void sendUserDeletedEvent(UserDto user) {
        UserEvent event = new UserEvent(UserEvent.EventType.DELETED, user);
        kafkaTemplate.send(TOPIC, event);
    }
}