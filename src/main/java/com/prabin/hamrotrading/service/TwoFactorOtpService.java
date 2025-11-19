package com.prabin.hamrotrading.service;

import com.prabin.hamrotrading.model.TwoFactorOTP;
import com.prabin.hamrotrading.model.User;

public interface TwoFactorOtpService {
    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);

    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP findById(String id);

    boolean verifyOtp(TwoFactorOTP twoFactorOTP, String otp);

    void deleteOtp(TwoFactorOTP otp);
}
