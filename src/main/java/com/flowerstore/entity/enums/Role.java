package com.flowerstore.entity.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER("Khách hàng"),
    ADMIN("Quản trị viên");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }
}
