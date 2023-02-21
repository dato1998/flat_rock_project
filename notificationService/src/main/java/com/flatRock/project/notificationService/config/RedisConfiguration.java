package com.flatRock.project.notificationService.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@Slf4j
public class RedisConfiguration {
    private final RedisClient redisClient;

    public RedisConfiguration(Environment environment) {
        String host = environment.getProperty("redis.host");
        this.redisClient = RedisClient
                .create(RedisURI.Builder
                        .redis(host, 6379)
                        .build());
    }

    @Bean
    public StatefulRedisPubSubConnection<String, String> redisSubscriber() {
        return redisClient.connectPubSub();
    }
}
