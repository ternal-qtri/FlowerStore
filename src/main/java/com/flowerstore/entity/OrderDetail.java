package com.flowerstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Đơn hàng không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_detail_order")
    )
    private Order order;

    @NotNull(message = "Sản phẩm không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_detail_product")
    )
    private Product product;

    /*
     * Giá snapshot tại thời điểm mua.
     * Không lấy trực tiếp từ Product.price khi xem lại đơn hàng.
     */
    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Giá không được âm"
    )
    @Digits(
            integer = 16,
            fraction = 2,
            message = "Giá không hợp lệ"
    )
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng tối thiểu là 1")
    @Column(nullable = false)
    @Builder.Default
    private Integer quantity = 1;

    @Size(max = 500, message = "Lời chúc tối đa 500 ký tự")
    @Column(name = "card_message", length = 500)
    private String cardMessage;
}