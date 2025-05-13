// analytics-service/src/main/java/com/example/analytics/controller/AnalyticsController.java
package com.example.analytics.controller;

import com.example.analytics.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/summary")
    public Map<String, Object> getAnalyticsSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalBooks", analyticsService.getTotalBooks());
        summary.put("totalUsers", analyticsService.getTotalUsers());

        Map<String, Integer> booksByAuthor = new HashMap<>();
        analyticsService.getBooksByAuthor().forEach((author, count) ->
                booksByAuthor.put(author, count.get()));
        summary.put("booksByAuthor", booksByAuthor);

        Map<String, Integer> actionCounts = new HashMap<>();
        analyticsService.getActionCounts().forEach((action, count) ->
                actionCounts.put(action, count.get()));
        summary.put("actionCounts", actionCounts);

        return summary;
    }
}