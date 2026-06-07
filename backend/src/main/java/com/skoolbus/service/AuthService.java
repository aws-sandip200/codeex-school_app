package com.skoolbus.service;

import com.skoolbus.dto.DashboardResponse;
import com.skoolbus.dto.auth.LoginRequest;
import com.skoolbus.dto.auth.LoginResponse;
import com.skoolbus.model.UserMaster;
import com.skoolbus.repository.UserMasterRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class AuthService {
    private final UserMasterRepository userMasterRepository;
    private final DashboardService dashboardService;

    public AuthService(UserMasterRepository userMasterRepository, DashboardService dashboardService) {
        this.userMasterRepository = userMasterRepository;
        this.dashboardService = dashboardService;
    }

    public LoginResponse login(LoginRequest request) {
        UserMaster user = userMasterRepository.findByUsernameIgnoreCaseAndActiveTrue(request.username().trim())
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED, "Invalid username or password"));

        if (!matches(request.password(), user.getPasswordHash())) {
            throw new ResponseStatusException(UNAUTHORIZED, "Invalid username or password");
        }

        DashboardResponse dashboard = dashboardService.dashboardForUser(user);
        return LoginResponse.from(user, dashboard);
    }

    private boolean matches(String rawPassword, String storedPasswordHash) {
        return storedPasswordHash != null && storedPasswordHash.equals(sha256(rawPassword));
    }

    private String sha256(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encoded = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(encoded);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 algorithm is unavailable", exception);
        }
    }
}
