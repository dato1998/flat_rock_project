package com.flatRock.project.productService.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flatRock.project.productService.dtos.OrderDTO;
import com.flatRock.project.productService.enums.OrderStatus;
import com.flatRock.project.productService.services.OrderService;
import io.lettuce.core.pubsub.RedisPubSubListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Listener implements RedisPubSubListener<String, String> {
    private final ObjectMapper mapper;
    private final OrderService orderService;

    public Listener(ObjectMapper mapper, OrderService orderService) {
        this.mapper = mapper;
        this.orderService = orderService;
    }

    @Override
    public void message(String channel, String message) {
        log.info("channel " + channel + " message " + message);
        OrderDTO orderDTO = extractPurchaseFromPubSubMessage(message);
        if (OrderStatus.CANCELED.equals(orderDTO.getStatus())) {
            orderService.update(orderDTO.getId(), orderDTO);
        } else {
            orderService.add(orderDTO);
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