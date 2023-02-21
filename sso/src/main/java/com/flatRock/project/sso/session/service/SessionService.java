package com.flatRock.project.sso.session.service;

import com.flatRock.project.sso.exceptions.BadCredentialsException;
import com.flatRock.project.sso.session.model.SessionEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class SessionService {
    private final JedisPool pool;

    @Value("${session.expiration.time}")
    private int sessionExpirationTime;

    @Value("${session.inactivity.time}")
    private long sessionInactivityTime;

    public SessionService(Environment environment) {
        String host = environment.getProperty("redis.host");
        int port = Integer.parseInt(Objects.requireNonNull(environment.getProperty("redis.port")));
        pool = new JedisPool(host, port);
    }

    public void addWithTokenKey(String key, SessionEntity sessionEntity) {
        try (Jedis jedis = pool.getResource()) {
            String token = jedis.get(sessionEntity.getUserId());
            if (token != null) {
                jedis.del(token);
            }
            Map<String, String> map = new HashMap<>();
            map.put("userId", sessionEntity.getUserId());
            map.put("email", sessionEntity.getEmail());
            if (sessionEntity.getUsername() != null) {
                map.put("username", sessionEntity.getUsername());
            }
            map.put("lastUpdatedDate", sessionEntity.getLastUpdatedDate().toString());
            jedis.hmset(key, map);
            jedis.expire(key, sessionExpirationTime);
        }
    }

    public void addWithUserIdKey(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(key, value);
            jedis.expire(key, sessionExpirationTime);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void deleteSessionUnit(HttpHeaders headers) {
        String token = getToken(headers);
        try (Jedis jedis = pool.getResource()) {
            jedis.del(token);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void updateSession(String token) {
        try (Jedis jedis = pool.getResource()) {
            long lastUpdatedDate = Long.parseLong(jedis.hget(token, "lastUpdatedDate"));
            long currentTime = new Date().toInstant().getEpochSecond();
            if ((currentTime - lastUpdatedDate) > sessionInactivityTime) {
                jedis.del(token);
                throw new BadCredentialsException("sorry but your session is already exhausted, please log in again");
            } else {
                jedis.hset(token, "lastUpdatedDate", String.valueOf(currentTime));
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public Map<String, String> getUserInformation(HttpHeaders headers) {
        Map<String, String> userInformation = new HashMap<>();
        String token = getToken(headers);
        try (Jedis jedis = pool.getResource()) {
            List<String> info = jedis.hmget(token, "userId", "username", "email");
            userInformation.put("userId", info.get(0));
            userInformation.put("username", info.get(1));
            userInformation.put("email", info.get(2));
            return userInformation;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public Map<String, Object> getEmail(String token) {
        Map<String, Object> userInformation = new HashMap<>();
        try (Jedis jedis = pool.getResource()) {
            List<String> info = jedis.hmget(token, "email");
            userInformation.put("email", info.get(0));
            return userInformation;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public String getUserId(HttpHeaders headers) {
        String token = getToken(headers);
        try (Jedis jedis = pool.getResource()) {
            return jedis.hget(token, "userId");
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    private String getToken(HttpHeaders headers) {
        String token = null;
        if (headers != null && headers.get("Authorization") != null) {
            String bearerToken = Objects.requireNonNull(headers.get("Authorization")).toString();
            token = bearerToken.substring(8, bearerToken.length() - 1);
        }
        return token;
    }
}
