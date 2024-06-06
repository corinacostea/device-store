package com.device.store.facade;

import com.device.store.request.PriceRequest;
import com.device.store.response.DeviceDetailsDto;
import com.device.store.response.DeviceDetailsUpdateDto;
import com.device.store.service.DeviceService;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class DeviceAdminFacade {

    private final DeviceService deviceService;

    public void addDevice(DeviceDetailsDto deviceDetailsDto) {
        deviceService.addDevice(deviceDetailsDto);
    }

    public void updateDevice(long externalId, DeviceDetailsUpdateDto deviceDetailsUpdateDto) {
        deviceService.updateDevice(externalId, deviceDetailsUpdateDto);
    }

    public void changePrice(long externalId, PriceRequest priceRequest) {
        deviceService.changePrice(externalId, priceRequest);
    }

}
