package com.prabin.hamrotrading.service.impl;

import com.prabin.hamrotrading.config.JwtProvider;
import com.prabin.hamrotrading.dto.response.AuthResponse;
import com.prabin.hamrotrading.model.User;
import com.prabin.hamrotrading.repo.UserRepository;
import com.prabin.hamrotrading.service.AppUserDetailsService;
import com.prabin.hamrotrading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AppUserDetailsService appUserDetailsService;

    @Override
    public AuthResponse saveUser(User user) throws Exception {

        Optional<User> isExistEmail = userRepository.findByEmail(user.getEmail());

        if (isExistEmail.isPresent()) {
            throw new Exception("User with this email " + user.getEmail() + " already exists!");
        }

//        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            throw new Exception("User with this email already exists!");
//        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());
        newUser.setPhone(user.getPhone());
        userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Register Success!");
        return res;
    }

    @Override
    public AuthResponse login(User user) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login Success!");
        return res;
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = appUserDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

    }
}
