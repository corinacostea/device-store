package com.device.store.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "device_order")
public class DeviceOrder extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_person")
    private String deliveryPerson;

    @Column(name = "delivery_phone")
    private String deliveryPhone;

    @Column(name = "delivery_details")
    private String deliveryDetails;

    @Column(name = "delivery_price")
    private Double deliveryPrice;

    @Column(name = "merchant_transaction_id")
    private String merchantTransactionId;

    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    private Device device;

    public enum Status {
        WAITING_FOR_PAYMENT, ORDER_FAILED, ORDER_PLACED
    }

}


