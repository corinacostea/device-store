package com.device.store.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceSummaryDto {

    private String name;
    private Double finalPrice;
    private Double referencePrice;
    private String thumbnail;

}
