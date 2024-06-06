package com.device.store.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class DeviceDetailsUpdateDto {

    private String name;
    private String details;
    private Double finalPrice;
    private Double referencePrice;
    private String thumbnail;
    private Integer stockNumber;
}
