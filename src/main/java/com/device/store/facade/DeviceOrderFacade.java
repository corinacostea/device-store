package com.device.store.facade;

import com.device.store.request.DevicePayRequest;
import com.device.store.response.OrderDetailsDto;
import com.device.store.service.DeviceOrderService;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class DeviceOrderFacade {

    private final DeviceOrderService deviceOrderService;

    public OrderDetailsDto buyDevice(DevicePayRequest devicePayRequest) {
        return deviceOrderService.buyDevice(devicePayRequest);
    }
}
