package com.skoolbus.controller;

import com.skoolbus.model.DeviceLocation;
import com.skoolbus.service.DeviceLocationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device-location")
public class DeviceLocationController {
    private final DeviceLocationService service;

    public DeviceLocationController(DeviceLocationService service) {
        this.service = service;
    }

    @PostMapping("/track-now")
    public DeviceLocation trackNow() {
        return service.trackCurrentLocation();
    }

    @GetMapping("/latest")
    public List<DeviceLocation> latest() {
        return service.latestLocations();
    }
}
