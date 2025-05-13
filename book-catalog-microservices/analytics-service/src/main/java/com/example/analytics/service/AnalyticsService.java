// analytics-service/src/main/java/com/example/analytics/service/AnalyticsService.java
package com.example.analytics.service;

import com.example.analytics.dto.Book;
import com.example.analytics.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AnalyticsService {
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsService.class);

    // Counters for analytics
    private final AtomicInteger totalBooks = new AtomicInteger(0);
    private final AtomicInteger totalUsers = new AtomicInteger(0);
    private final Map<String, AtomicInteger> booksByAuthor = new ConcurrentHashMap<>();
    private final Map<String, AtomicInteger> actionCounts = new ConcurrentHashMap<>();

    public void recordBookCreated(Book book) {
        logger.info("Recording book created: {}", book.getTitle());
        totalBooks.incrementAndGet();
        booksByAuthor.computeIfAbsent(book.getAuthor(), k -> new AtomicInteger(0)).incrementAndGet();
        incrementActionCount("BOOK_CREATED");
    }

    public void recordBookUpdated(Book book) {
        logger.info("Recording book updated: {}", book.getTitle());
        incrementActionCount("BOOK_UPDATED");
    }

    public void recordBookDeleted(Book book) {
        logger.info("Recording book deleted: {}", book.getTitle());
        totalBooks.decrementAndGet();
        booksByAuthor.computeIfPresent(book.getAuthor(), (k, v) -> {
            int newCount = v.decrementAndGet();
            return newCount <= 0 ? null : v;
        });
        incrementActionCount("BOOK_DELETED");
    }

    public void recordUserRegistered(UserDto user) {
        logger.info("Recording user registered: {}", user.getUsername());
        totalUsers.incrementAndGet();
        incrementActionCount("USER_REGISTERED");
    }

    public void recordUserUpdated(UserDto user) {
        logger.info("Recording user updated: {}", user.getUsername());
        incrementActionCount("USER_UPDATED");
    }

    public void recordUserDeleted(UserDto user) {
        logger.info("Recording user deleted: {}", user.getUsername());
        totalUsers.decrementAndGet();
        incrementActionCount("USER_DELETED");
    }

    private void incrementActionCount(String action) {
        actionCounts.computeIfAbsent(action, k -> new AtomicInteger(0)).incrementAndGet();
    }

    // Methods to retrieve analytics data
    public int getTotalBooks() {
        return totalBooks.get();
    }

    public int getTotalUsers() {
        return totalUsers.get();
    }

    public Map<String, AtomicInteger> getBooksByAuthor() {
        return booksByAuthor;
    }

    public Map<String, AtomicInteger> getActionCounts() {
        return actionCounts;
    }
}