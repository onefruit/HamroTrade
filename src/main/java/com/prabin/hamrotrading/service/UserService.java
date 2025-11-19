package com.prabin.hamrotrading.service;

import com.prabin.hamrotrading.dto.response.AuthResponse;
import com.prabin.hamrotrading.enums.VerificationType;
import com.prabin.hamrotrading.model.User;

public interface UserService {
    AuthResponse saveUser(User user) throws Exception;
    AuthResponse login(User user) throws Exception;

    User findUserByJwt(String jwt);
    User findUserByEmail(String email);
    User findUserById(Long id);

    User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);

    User updatePassword(User user, String newPassword);
}
