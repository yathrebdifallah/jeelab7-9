// book-service/src/main/java/com/example/book/client/UserDto.java
package com.example.book.client;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String username;
    private String email;
}