package com.se.user.mapper;

import com.se.user.entity.User;
import com.se.user.dto.UserDto;

public interface ProfileMapper {
    boolean supports(User user);
    UserDto.ProfileDto mapProfile(User user);
}
