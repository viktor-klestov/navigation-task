package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("location")
@RequiredArgsConstructor
public class MobileStationController {
    private final MobileLocationService service;

    @PostMapping("/")
    public void acceptNotification(@RequestBody BaseStationNotification notification) {
        service.acceptNotification(notification);
    }

    @GetMapping("{mobileStationId}")
    public MobileLocationResponse getLocation(@PathVariable("mobileStationId") UUID mobileStationId) {
        return service.findLocation(mobileStationId);
    }
}
