package com.flatRock.project.notificationService.controller;

import com.flatRock.project.notificationService.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {

    private NotificationService service;

    @PostMapping("/mail")
    public String send(@RequestParam String email, @RequestParam String subject, @RequestParam String context) {
        return service.sendNotification(email, subject, context);
    }

    @PostMapping("/sms")
    public String send(@RequestParam String to, @RequestParam String context) {
        return service.sendSMS(to, context);
    }
}
