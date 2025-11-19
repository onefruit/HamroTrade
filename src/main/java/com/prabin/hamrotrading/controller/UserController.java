package com.prabin.hamrotrading.controller;

import com.prabin.hamrotrading.model.User;
import com.prabin.hamrotrading.service.EmailService;
import com.prabin.hamrotrading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @PatchMapping("/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
//3:15