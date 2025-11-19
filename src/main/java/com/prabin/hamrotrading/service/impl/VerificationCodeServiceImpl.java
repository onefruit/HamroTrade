package com.prabin.hamrotrading.service.impl;

import com.prabin.hamrotrading.enums.VerificationType;
import com.prabin.hamrotrading.model.User;
import com.prabin.hamrotrading.model.VerificationCode;
import com.prabin.hamrotrading.repo.VerificationCodeRepo;
import com.prabin.hamrotrading.service.VerificationCodeService;
import com.prabin.hamrotrading.utils.OtpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@RequestMapping("/api/verification-code")
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {

    private final VerificationCodeRepo verificationCodeRepo;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOTP());
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);
        return verificationCodeRepo.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {
        return verificationCodeRepo.findById(id).orElseThrow(()-> new Exception("verification not found"));
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepo.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepo.delete(verificationCode);
    }
}
