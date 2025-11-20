package com.prabin.hamrotrading.repo;

import com.prabin.hamrotrading.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {
}
