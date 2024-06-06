package com.device.store.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceRequest {
    private JsonNullable<Double> finalPrice;
    private JsonNullable<Double> referencePrice;
}
