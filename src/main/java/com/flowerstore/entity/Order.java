package com.flowerstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.flowerstore.entity.enums.OrderStatus;
import com.flowerstore.entity.enums.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Mã đơn hàng không được để trống")
    @Size(max = 30, message = "Mã đơn hàng tối đa 30 ký tự")
    @Column(name = "order_code", nullable = false, unique = true, length = 30)
    private String orderCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_order_user")
    )
    private User user;

    @NotBlank(message = "Tên người nhận không được để trống")
    @Size(max = 100, message = "Tên người nhận tối đa 100 ký tự")
    @Column(name = "recipient_name", nullable = false, length = 100)
    private String recipientName;

    @NotBlank(message = "Số điện thoại người nhận không được để trống")
    @Pattern(
            regexp = "^0[0-9]{9,10}$",
            message = "Số điện thoại không hợp lệ"
    )
    @Column(name = "recipient_phone", nullable = false, length = 20)
    private String recipientPhone;

    @NotBlank(message = "Địa chỉ nhận hàng không được để trống")
    @Size(max = 255, message = "Địa chỉ tối đa 255 ký tự")
    @Column(name = "recipient_address", nullable = false, length = 255)
    private String recipientAddress;

    @Size(max = 500, message = "Ghi chú tối đa 500 ký tự")
    @Column(length = 500)
    private String note;

    @NotNull(message = "Tổng tiền không được để trống")
    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Tổng tiền không được âm"
    )
    @Digits(
            integer = 16,
            fraction = 2,
            message = "Tổng tiền không hợp lệ"
    )
    @Column(name = "total_amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal totalAmount;

    @NotNull(message = "Phương thức thanh toán không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 30)
    @Builder.Default
    private PaymentMethod paymentMethod = PaymentMethod.COD;

    @NotNull(message = "Trạng thái đơn hàng không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 30)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.PLACED;

    @Column(name = "ordered_at")
    private LocalDateTime orderedAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (orderedAt == null) {
            orderedAt = LocalDateTime.now();
        }

        if (paymentMethod == null) {
            paymentMethod = PaymentMethod.COD;
        }

        if (orderStatus == null) {
            orderStatus = OrderStatus.PLACED;
        }
    }
}