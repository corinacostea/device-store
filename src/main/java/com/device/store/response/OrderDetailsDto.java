package com.device.store.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    private String name;
    private String details;
    private Long deviceExternalId;
    private Double finalPrice;
    private String deliveryAddress;
    private String deliveryPerson;
    private String deliveryPhone;
    private String deliveryDetails;
}
