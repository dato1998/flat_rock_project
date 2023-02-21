package com.flatRock.project.notificationService.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flatRock.project.notificationService.service.NotificationService;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {
    private final NotificationService notificationService;
    private final ObjectMapper mapper;
    private final static String CHANNEL_NAME = "place_order";
    private final StatefulRedisPubSubConnection<String, String> pubSubCommands;

    public OrderListener(NotificationService notificationService,
                         ObjectMapper mapper,
                         StatefulRedisPubSubConnection<String, String> pubSubCommands) {
        this.notificationService = notificationService;
        this.mapper = mapper;
        this.pubSubCommands = pubSubCommands;
        this.startListening();
    }

    private void startListening() {
        pubSubCommands.addListener(new Listener(mapper, notificationService));
        pubSubCommands.sync().subscribe(CHANNEL_NAME);
    }
}