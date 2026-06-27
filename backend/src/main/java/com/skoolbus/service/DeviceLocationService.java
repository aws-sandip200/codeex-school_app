package com.skoolbus.service;

import com.skoolbus.config.DeviceLocationProperties;
import com.skoolbus.dto.location.IplocateResponse;
import com.skoolbus.model.DeviceLocation;
import com.skoolbus.repository.DeviceLocationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.List;

@Service
public class DeviceLocationService {
    private final DeviceLocationProperties properties;
    private final DeviceLocationRepository repository;
    private final RestClient restClient;

    public DeviceLocationService(DeviceLocationProperties properties, DeviceLocationRepository repository, RestClient.Builder restClientBuilder) {
        this.properties = properties;
        this.repository = repository;
        this.restClient = restClientBuilder.baseUrl(properties.getBaseUrl()).build();
    }

    @Scheduled(cron = "0 0 * * * *")
    public void trackHourlyLocation() {
        if (properties.isEnabled()) {
            trackCurrentLocation();
        }
    }

    public DeviceLocation trackCurrentLocation() {
        IplocateResponse response = restClient.get()
                .uri(uriBuilder -> {
                    String path = StringUtils.hasText(properties.getTargetIp())
                            ? "/lookup/{ip}"
                            : "/lookup";
                    var builder = uriBuilder.path(path)
                            .queryParam("include", "ip,country,country_code,city,subdivision,postal_code,time_zone,latitude,longitude");
                    if (StringUtils.hasText(properties.getApiKey())) {
                        builder.queryParam("apikey", properties.getApiKey());
                    }
                    return StringUtils.hasText(properties.getTargetIp())
                            ? builder.build(properties.getTargetIp())
                            : builder.build();
                })
                .retrieve()
                .body(IplocateResponse.class);

        if (response == null || !StringUtils.hasText(response.ip())) {
            throw new IllegalStateException("IPLocate did not return a device IP address");
        }

        DeviceLocation location = new DeviceLocation();
        location.setIpAddress(response.ip());
        location.setCountry(response.country());
        location.setCountryCode(response.countryCode());
        location.setCity(response.city());
        location.setSubdivision(response.subdivision());
        location.setPostalCode(response.postalCode());
        location.setTimeZone(response.timeZone());
        location.setLatitude(response.latitude());
        location.setLongitude(response.longitude());
        location.setTrackedAt(Instant.now());
        return repository.save(location);
    }

    public List<DeviceLocation> latestLocations() {
        return repository.findTop24ByOrderByTrackedAtDesc();
    }
}
