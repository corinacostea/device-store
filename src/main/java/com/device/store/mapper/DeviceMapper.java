package com.device.store.mapper;

import com.device.store.model.Device;
import com.device.store.request.PriceRequest;
import com.device.store.response.DeviceDetailsDto;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import static com.device.store.util.JsonNullableUtils.applyPatch;

@Component
public class DeviceMapper {

    public Device getDevice(DeviceDetailsDto deviceDetailsDto) {
        return Device.builder()
                .externalId(deviceDetailsDto.getExternalId())
                .name(deviceDetailsDto.getName())
                .category(Device.Category.valueOf(deviceDetailsDto.getCategory()))
                .information(deviceDetailsDto.getDetails())
                .finalPrice(deviceDetailsDto.getFinalPrice())
                .referencePrice(deviceDetailsDto.getReferencePrice())
                .thumbnail(deviceDetailsDto.getThumbnail())
                .stockNumber(deviceDetailsDto.getStockNumber())
                .build();
    }

    public Device applyPatchToEntity(PriceRequest request, Device entity) {
        Validate.notNull(request, "Price patch request is null.");
        Validate.notNull(entity, "Device is null.");
        applyPatch(request::getFinalPrice, entity::setFinalPrice);
        applyPatch(request::getReferencePrice, entity::setReferencePrice);
        return entity;
    }

}
