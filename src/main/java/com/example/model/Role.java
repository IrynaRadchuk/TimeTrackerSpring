package com.example.model;

public enum Role {
    GUEST(0), USER(1), ADMIN(2);
    private int id;

    Role(int id) {
        this.id = id;
    }
}
