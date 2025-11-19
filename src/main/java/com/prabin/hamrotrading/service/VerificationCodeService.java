package com.prabin.hamrotrading.service;

import com.prabin.hamrotrading.enums.VerificationType;
import com.prabin.hamrotrading.model.User;
import com.prabin.hamrotrading.model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
