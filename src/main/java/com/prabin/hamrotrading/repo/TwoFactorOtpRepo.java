package com.prabin.hamrotrading.repo;

import com.prabin.hamrotrading.model.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepo extends JpaRepository<TwoFactorOTP, String> {
    TwoFactorOTP findByUserId(Long id);
}
