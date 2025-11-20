package com.prabin.hamrotrading.repo;

import com.prabin.hamrotrading.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUserId(Long id);

}
