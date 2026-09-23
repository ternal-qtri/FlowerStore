package com.flowerstore.entity.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PLACED("Đã đặt"),
    CONFIRMED("Đã xác nhận"),
    DELIVERING("Đang giao"),
    COMPLETED("Hoàn thành"),
    CANCELLED("Đã hủy");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }
}
