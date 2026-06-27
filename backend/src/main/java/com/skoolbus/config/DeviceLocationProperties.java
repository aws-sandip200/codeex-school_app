package com.skoolbus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.device-location")
public class DeviceLocationProperties {
    /**
     * Enables the hourly IPLocate lookup job.
     */
    private boolean enabled = false;

    /**
     * IPLocate API key. Create one at https://iplocate.io/signup.
     */
    private String apiKey;

    /**
     * IP address to track. Leave blank to let IPLocate resolve the caller's public IP.
     */
    private String targetIp;

    /**
     * IPLocate API base URL.
     */
    private String baseUrl = "https://iplocate.io/api";

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }
    public String getTargetIp() { return targetIp; }
    public void setTargetIp(String targetIp) { this.targetIp = targetIp; }
    public String getBaseUrl() { return baseUrl; }
    public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
}
