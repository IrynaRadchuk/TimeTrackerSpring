package com.example.model.dto;

import java.util.Objects;

public class AdminRequestDTO {
    private Long userId;
    private String activityName;

    public AdminRequestDTO(Long userId, String activityName) {
        this.userId = userId;
        this.activityName = activityName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminRequestDTO)) return false;
        AdminRequestDTO that = (AdminRequestDTO) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(activityName, that.activityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, activityName);
    }
}
