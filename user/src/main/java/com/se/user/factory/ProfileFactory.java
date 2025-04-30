package com.se.user.factory;

import com.se.user.dto.RegisterRequest;
import com.se.user.entity.User;

/**
 * Factory interface để tạo profile dựa trên role của user
 */
public interface ProfileFactory {
    /**
     * Kiểm tra factory có hỗ trợ role này không
     * @param user User cần kiểm tra
     * @return true nếu factory hỗ trợ tạo profile cho role này
     */
    boolean supports(User user);
    
    /**
     * Tạo profile tương ứng cho user
     * @param user User cần tạo profile
     * @param request Thông tin đăng ký từ người dùng
     */
    void createProfile(User user, RegisterRequest request);
}
