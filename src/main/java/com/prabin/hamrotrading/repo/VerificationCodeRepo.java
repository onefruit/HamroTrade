package com.prabin.hamrotrading.repo;

import com.prabin.hamrotrading.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepo extends JpaRepository<VerificationCode, Long> {
    VerificationCode findByUserId(Long userId);
}
