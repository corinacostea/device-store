package com.device.store.facade;

import com.device.store.request.DeviceaBuyRequest;
import com.device.store.response.OrderDetailsDto;
import com.device.store.service.DeviceOrderService;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class DeviceOrderFacade {

    private final DeviceOrderService deviceOrderService;

    public OrderDetailsDto buyDevice(DeviceaBuyRequest deviceaBuyRequest) {
        return deviceOrderService.buyDevice(deviceaBuyRequest);
    }

    public String payDevice(long orderId) {
        return deviceOrderService.payDevice(orderId);
    }
}
