package com.prabin.hamrotrading.controller;

import com.prabin.hamrotrading.enums.WalletTransactionType;
import com.prabin.hamrotrading.model.Order;
import com.prabin.hamrotrading.model.User;
import com.prabin.hamrotrading.model.Wallet;
import com.prabin.hamrotrading.model.WalletTransaction;
import com.prabin.hamrotrading.service.OrderService;
import com.prabin.hamrotrading.service.UserService;
import com.prabin.hamrotrading.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization")String jwt){
        User user = userService.findUserByJwt(jwt);
        Wallet userWallet = walletService.getUserWallet(user);
        return new ResponseEntity<>(userWallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("{walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfer(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long walletId,
            @RequestBody WalletTransaction req
            ) throws Exception {

        User senderUser = userService.findUserByJwt(jwt);
        Wallet receiverWallet = walletService.findWalletById(walletId);
        Wallet wallet = walletService.walletToWalletTransfer(senderUser, receiverWallet, req.getAmount());

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long orderId
    ) throws Exception {

        User user = userService.findUserByJwt(jwt);
        Order order = orderService.getOrderById(orderId);

        Wallet wallet = walletService.payOrderPayment(order, user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }




}
