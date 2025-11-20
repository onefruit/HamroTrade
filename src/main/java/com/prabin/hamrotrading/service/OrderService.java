package com.prabin.hamrotrading.service;

import com.prabin.hamrotrading.enums.OrderType;
import com.prabin.hamrotrading.model.Coin;
import com.prabin.hamrotrading.model.Order;
import com.prabin.hamrotrading.model.OrderItem;
import com.prabin.hamrotrading.model.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem item, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId, OrderType OrderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user);
}
