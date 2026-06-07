package com.skoolbus.dto.auth;

import com.skoolbus.dto.DashboardResponse;
import com.skoolbus.model.UserMaster;
import com.skoolbus.model.UserRole;

public record LoginResponse(Long userId, String username, String displayName, UserRole role, Long studentId, Long busId, DashboardResponse dashboard) {
    public static LoginResponse from(UserMaster user, DashboardResponse dashboard) {
        return new LoginResponse(user.getId(), user.getUsername(), user.getDisplayName(), user.getRole(), user.getStudentId(), user.getBusId(), dashboard);
    }
}
