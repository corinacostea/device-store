package com.device.store.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DevicePayRequest {
    private Long customerId;
    private Long externalId;
    private String deliveryAddress;
    private String deliveryPerson;
    private String deliveryPhone;
    private String deliveryDetails;
}
