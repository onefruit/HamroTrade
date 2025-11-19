package com.prabin.hamrotrading.service.impl;

import com.prabin.hamrotrading.config.JwtProvider;
import com.prabin.hamrotrading.dto.response.AuthResponse;
import com.prabin.hamrotrading.enums.VerificationType;
import com.prabin.hamrotrading.model.TwoFactorAuth;
import com.prabin.hamrotrading.model.TwoFactorOTP;
import com.prabin.hamrotrading.model.User;
import com.prabin.hamrotrading.repo.UserRepository;
import com.prabin.hamrotrading.service.AppUserDetailsService;
import com.prabin.hamrotrading.service.EmailService;
import com.prabin.hamrotrading.service.TwoFactorOtpService;
import com.prabin.hamrotrading.service.UserService;
import com.prabin.hamrotrading.utils.OtpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AppUserDetailsService appUserDetailsService;
    private final TwoFactorOtpService twoFactorOtpService;
    private final EmailService emailService;

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

        User authUser = userRepository.findByEmail(email).orElse(null);

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        if (user.getTwoFactorAuth().isEnabled()) {
            AuthResponse res = new AuthResponse();
            res.setMessage("Two factor auth is enabled.");
            res.setTwoFactorAuthEnable(true);
            String otp = OtpUtils.generateOTP();

            TwoFactorOTP oldOtp = twoFactorOtpService.findByUser(user.getId());
            if (oldOtp != null) {
                twoFactorOtpService.deleteOtp(oldOtp);
            }

            TwoFactorOTP latest = twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);
            emailService.sendVerificationOtpEmail(user.getEmail(), otp);

            res.setSession(latest.getId());
            return res;
        }

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login Success!");
        return res;
    }

    @Override
    public User findUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email " + email));

    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email " + email));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);
        user.setTwoFactorAuth(twoFactorAuth);
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepository.save(user);
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
