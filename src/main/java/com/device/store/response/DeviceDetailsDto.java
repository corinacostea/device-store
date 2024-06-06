package com.device.store.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
public class DeviceDetailsDto {

    private String name;
    private String details;
    private Long externalId;
    private Double finalPrice;
    private Double referencePrice;
    private String thumbnail;
    private String category;
    private Integer stockNumber;
}
