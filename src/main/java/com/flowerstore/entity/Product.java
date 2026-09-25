package com.flowerstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Danh mục không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_product_category")
    )
    private Category category;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 150, message = "Tên sản phẩm tối đa 150 ký tự")
    @Column(nullable = false, length = 150)
    private String name;

    @NotBlank(message = "Slug không được để trống")
    @Size(max = 180, message = "Slug tối đa 180 ký tự")
    @Column(nullable = false, unique = true, length = 180)
    private String slug;

    @NotBlank(message = "Ảnh sản phẩm không được để trống")
    @Size(max = 500, message = "URL ảnh tối đa 500 ký tự")
    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Giá sản phẩm không được âm"
    )
    @Digits(
            integer = 16,
            fraction = 2,
            message = "Giá sản phẩm không hợp lệ"
    )
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Giá cũ không được âm"
    )
    @Digits(
            integer = 16,
            fraction = 2,
            message = "Giá cũ không hợp lệ"
    )
    @Column(name = "old_price", precision = 18, scale = 2)
    private BigDecimal oldPrice;

    @Size(max = 50, message = "Badge tối đa 50 ký tự")
    @Column(name = "badge_tag", length = 50)
    private String badgeTag;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Size(max = 500, message = "Thành phần tối đa 500 ký tự")
    @Column(length = 500)
    private String composition;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "product")
    @Builder.Default
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (active == null) {
            active = true;
        }
    }
}