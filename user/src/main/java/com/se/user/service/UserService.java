package com.se.user.service;

import com.se.user.dto.UserDto;
import com.se.user.entity.User;
import com.se.user.exception.AuthenticationException;
import com.se.user.exception.UserNotFoundException;
import com.se.user.mapper.UserMapper;
import com.se.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::mapToUserDto).collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.mapToUserDto(user);
    }

    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return userMapper.mapToUserDto(user);
        }
        throw new AuthenticationException("Người dùng chưa đăng nhập");
    }

    public UserDto updateUserStatus(Long id, boolean active) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setActive(active);
        User updatedUser = userRepository.save(user);
        return userMapper.mapToUserDto(updatedUser);
    }
}