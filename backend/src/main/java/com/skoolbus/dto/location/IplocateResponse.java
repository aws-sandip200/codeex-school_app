package com.skoolbus.dto.location;

import com.fasterxml.jackson.annotation.JsonProperty;

public record IplocateResponse(
        String ip,
        String country,
        @JsonProperty("country_code") String countryCode,
        String city,
        String subdivision,
        @JsonProperty("postal_code") String postalCode,
        @JsonProperty("time_zone") String timeZone,
        Double latitude,
        Double longitude
) {}
