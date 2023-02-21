package com.flatRock.project.productService.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flatRock.project.productService.services.OrderService;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {
    private final OrderService orderService;
    private final ObjectMapper mapper;
    private final static String CHANNEL_NAME = "place_order";
    private final StatefulRedisPubSubConnection<String, String> pubSubCommands;

    public OrderListener(OrderService orderService,
                         ObjectMapper mapper,
                         StatefulRedisPubSubConnection<String, String> pubSubCommands) {
        this.orderService = orderService;
        this.mapper = mapper;
        this.pubSubCommands = pubSubCommands;
        this.startListening();
    }

    private void startListening() {
        pubSubCommands.addListener(new Listener(mapper, orderService));
        pubSubCommands.sync().subscribe(CHANNEL_NAME);
    }
}