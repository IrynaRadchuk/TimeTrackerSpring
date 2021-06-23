package com.example.model;

public enum RoleName {
    USER(1), ADMIN(2);
    private long id;

    RoleName(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
