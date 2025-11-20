package com.prabin.hamrotrading.service;

import com.prabin.hamrotrading.model.Order;
import com.prabin.hamrotrading.model.User;
import com.prabin.hamrotrading.model.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet, Long amount);
    Wallet findWalletById(Long id);
    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;

}
