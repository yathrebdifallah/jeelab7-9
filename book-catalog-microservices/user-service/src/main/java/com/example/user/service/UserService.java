// user-service/src/main/java/com/example/user/service/UserService.java
package com.example.user.service;

import com.example.user.dto.UserDto;
import com.example.user.dto.UserEvent;
import com.example.user.kafka.UserEventProducer;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEventProducer eventProducer;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getUserById(Integer id) {
        return userRepository.findById(id)
                .map(this::convertToDto);
    }

    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::convertToDto);
    }

    public UserDto createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User savedUser = userRepository.save(user);
        UserDto userDto = convertToDto(savedUser);
        eventProducer.sendUserRegisteredEvent(userDto);
        return userDto;
    }

    public UserDto updateUser(Integer id, User userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    // Check if username is being changed and if it's already taken
                    if (!existingUser.getUsername().equals(userDetails.getUsername()) &&
                            userRepository.existsByUsername(userDetails.getUsername())) {
                        throw new RuntimeException("Username already exists");
                    }

                    // Check if email is being changed and if it's already taken
                    if (!existingUser.getEmail().equals(userDetails.getEmail()) &&
                            userRepository.existsByEmail(userDetails.getEmail())) {
                        throw new RuntimeException("Email already exists");
                    }

                    existingUser.setUsername(userDetails.getUsername());
                    existingUser.setEmail(userDetails.getEmail());

                    if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                        existingUser.setPassword(userDetails.getPassword());
                    }

                    User updatedUser = userRepository.save(existingUser);
                    UserDto userDto = convertToDto(updatedUser);
                    eventProducer.sendUserUpdatedEvent(userDto);
                    return userDto;
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        userRepository.delete(user);
        UserDto userDto = convertToDto(user);
        eventProducer.sendUserDeletedEvent(userDto);
    }

    private UserDto convertToDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}