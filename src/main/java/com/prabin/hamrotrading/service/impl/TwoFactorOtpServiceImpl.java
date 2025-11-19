package com.prabin.hamrotrading.service.impl;

import com.prabin.hamrotrading.model.TwoFactorOTP;
import com.prabin.hamrotrading.model.User;
import com.prabin.hamrotrading.repo.TwoFactorOtpRepo;
import com.prabin.hamrotrading.service.TwoFactorOtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService {

    private TwoFactorOtpRepo twoFactorOtpRepo;


    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
        twoFactorOTP.setOtp(otp);
        twoFactorOTP.setJwt(jwt);
        twoFactorOTP.setId(id);
        twoFactorOTP.setUser(user);

        return twoFactorOtpRepo.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return twoFactorOtpRepo.findByUserId(userId);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        return twoFactorOtpRepo.findById(id).orElse(null);
    }

    @Override
    public boolean verifyOtp(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getOtp().equals(otp);
    }

    @Override
    public void deleteOtp(TwoFactorOTP otp) {
        twoFactorOtpRepo.delete(otp);
    }
}
