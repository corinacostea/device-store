package com.device.store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "device")
public class Device extends BaseEntity {

    @Column(name = "external_id")
    private Long externalId;

    @Column(name = "name")
    private String name;

    @Column(name = "information")
    private String information;

    @Column(name = "final_price")
    private Double finalPrice;

    @Column(name = "reference_price")
    private Double referencePrice;

    @Column(name = "reservation_expiration_date")
    private LocalDateTime reservationExpirationDate;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock")
    private Stock stock;

    @Column(name = "stock_number")
    private Integer stockNumber;

    public enum Category {
        PHONES, ELECTRONICS, COMPUTERS
    }

    public enum Stock {
        IN_STOCK, LIMITED_STOCK, OUT_OF_STOCK;
    }
}
