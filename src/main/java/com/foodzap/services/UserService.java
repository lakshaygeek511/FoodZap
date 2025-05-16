package com.foodzap.services;

import com.foodzap.dto.UserDto;
import com.foodzap.models.User;
import com.foodzap.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertToDto).orElse(null);
    }

    public UserDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(this::convertToDto).orElse(null);
    }

    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        
        User user = convertToEntity(userDto);
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> existingUser = userRepository.findById(id);
        
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            
            // Update fields but keep the same ID
            user.setName(userDto.getName());
            user.setPhone(userDto.getPhone());
            user.setAddress(userDto.getAddress());
            
            // If email is being changed, check if it's already in use
            if (!user.getEmail().equals(userDto.getEmail()) && 
                userRepository.existsByEmail(userDto.getEmail())) {
                throw new RuntimeException("Email already in use");
            }
            user.setEmail(userDto.getEmail());
            
            // Update password if provided, otherwise keep the existing password
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                user.setPassword(userDto.getPassword());
            }
            
            User updatedUser = userRepository.save(user);
            return convertToDto(updatedUser);
        }
        
        return null;
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Conversion methods
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        // We don't include password in the DTO for security reasons
        return dto;
    }

    private User convertToEntity(UserDto dto) {
        User user = new User();
        if (dto.getId() != null) {
            user.setId(dto.getId());
        }
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        
        // Set password from DTO
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        } else {
            // Provide a default password or throw an exception
            throw new RuntimeException("Password is required");
        }
        
        return user;
    }
} 