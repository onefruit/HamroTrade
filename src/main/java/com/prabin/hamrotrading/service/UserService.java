package com.prabin.hamrotrading.service;

import com.prabin.hamrotrading.dto.response.AuthResponse;
import com.prabin.hamrotrading.model.User;

public interface UserService {
    AuthResponse saveUser(User user) throws Exception;
    AuthResponse login(User user) throws Exception;

}
