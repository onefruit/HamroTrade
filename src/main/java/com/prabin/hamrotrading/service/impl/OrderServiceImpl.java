package com.prabin.hamrotrading.service.impl;

import com.prabin.hamrotrading.enums.OrderStatus;
import com.prabin.hamrotrading.enums.OrderType;
import com.prabin.hamrotrading.model.Coin;
import com.prabin.hamrotrading.model.Order;
import com.prabin.hamrotrading.model.OrderItem;
import com.prabin.hamrotrading.model.User;
import com.prabin.hamrotrading.repo.OrderItemRepository;
import com.prabin.hamrotrading.repo.OrderRepository;
import com.prabin.hamrotrading.service.OrderService;
import com.prabin.hamrotrading.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final WalletService walletService;

    @Override
    public Order createOrder(User user, OrderItem item, OrderType orderType) {
        BigDecimal price = item.getCoin().getCurrentPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

        Order order = new Order();
        order.setUser(user);
        order.setOrderType(orderType);
        order.setOrderItem(item);
        order.setPrice(price);
        order.setTimestamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId).orElseThrow(() -> new Exception("Order not found"));
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType OrderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin, double quantity, BigDecimal buyPrice, BigDecimal sellPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public Order buyAsset(Coin coin, double quantity, User user) throws Exception {
        if (quantity <= 0) {
            throw new Exception("quantity should be greater than 0");
        }
        BigDecimal buyPrice = coin.getCurrentPrice();
        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, BigDecimal.valueOf(0));

        Order order = createOrder(user, orderItem, OrderType.BUY);
        orderItem.setOrder(order);
        walletService.payOrderPayment(order, user);
        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder = orderRepository.save(order);

        //create asset later

        return savedOrder;
    }

    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
        return null;
    }
}
