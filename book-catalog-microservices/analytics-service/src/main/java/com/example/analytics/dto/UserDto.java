// analytics-service/src/main/java/com/example/analytics/dto/UserDto.java
package com.example.analytics.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String email;
}