package com.se.user.Accountant.Interface;

import com.se.user.Accountant.Request.UpdateProfileReq;

public interface IAccountant {
    String updateProfile(String username, UpdateProfileReq request);
}
