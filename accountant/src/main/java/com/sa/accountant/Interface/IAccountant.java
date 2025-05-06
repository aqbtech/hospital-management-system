package com.sa.accountant.Interface;


import com.sa.accountant.Request.UpdateProfileReq;

public interface IAccountant {
    String updateProfile(String username, UpdateProfileReq request);
}
