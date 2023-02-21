package com.flatRock.project.notificationService.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flatRock.project.notificationService.data.dto.OrderDTO;
import com.flatRock.project.notificationService.data.enums.OrderStatus;
import com.flatRock.project.notificationService.service.NotificationService;
import io.lettuce.core.pubsub.RedisPubSubListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Listener implements RedisPubSubListener<String, String> {
    private final ObjectMapper mapper;
    private final NotificationService notificationService;

    public Listener(ObjectMapper mapper, NotificationService notificationService) {
        this.mapper = mapper;
        this.notificationService = notificationService;
    }

    @Override
    public void message(String channel, String message) {
        log.info("channel " + channel + " message " + message);
        OrderDTO orderDTO = extractPurchaseFromPubSubMessage(message);
        if (orderDTO.getStatus().equals(OrderStatus.PENDING)) {
            notificationService.sendNotification(orderDTO.getClient().getEmail(), "Order status is Pending", "your order with id: " + orderDTO.getId() + " and price: " + orderDTO.getTotalPrice() + " was accepted and now in status pending");
            notificationService.sendSMS(orderDTO.getClient().getPhoneNumber(), "your order was accepted and now in status pending");
        } else if (orderDTO.getStatus().equals(OrderStatus.DELIVERED)) {
            notificationService.sendNotification(orderDTO.getClient().getEmail(), "Order status is Deliverd", "your order with id: " + orderDTO.getId() + " and price: " + orderDTO.getTotalPrice() + " was Delivered");
            notificationService.sendSMS(orderDTO.getClient().getPhoneNumber(), "your order was accepted and now in status pending");
        } else {
            notificationService.sendNotification(orderDTO.getClient().getEmail(), "Order status is Cancelled", "your order with id: " + orderDTO.getId() + " and price: " + orderDTO.getTotalPrice() + " was Cancelled");
            notificationService.sendSMS(orderDTO.getClient().getPhoneNumber(), "your order was cancelled and now in status pending");
        }
    }

    private OrderDTO extractPurchaseFromPubSubMessage(String message) {
        try {
            return mapper.readValue(message, OrderDTO.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void message(String pattern, String channel, String message) {
        log.info("MyChannel " + channel + " message " + message);
    }

    @Override
    public void subscribed(String channel, long count) {
        log.info("SUBChannel " + channel + " count " + count);
    }

    @Override
    public void psubscribed(String pattern, long count) {
        log.info("PUBChannel " + pattern + " message " + count);
    }

    @Override
    public void unsubscribed(String channel, long count) {

    }

    @Override
    public void punsubscribed(String pattern, long count) {

    }
}