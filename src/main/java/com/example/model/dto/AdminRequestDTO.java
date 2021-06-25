package com.example.model.dto;

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
}
