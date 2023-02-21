package com.flatRock.project.productService.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("single-sign-on")
public interface SingleSignOnServiceProxy {
    @PutMapping("/session/")
    void updateSession(@RequestParam String token);

    @GetMapping("/sso/user/info")
    Map<String, Object> getUserIdAndRole(@RequestParam final String token);
}
