package com.se.user.mapper;

import com.se.user.entity.User;
import com.se.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final List<ProfileMapper> profileMappers;

    public UserDto mapToUserDto(User user) {
        UserDto.ProfileDto profileDto = null;
        for (ProfileMapper mapper : profileMappers) {
            if (mapper.supports(user)) {
                profileDto = mapper.mapProfile(user);
                break;
            }
        }
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .lastLogin(user.getLastLogin())
                .active(user.getActive())
                .profile(profileDto)
                .build();
    }
}
