package com.device.store.facade;

import com.device.store.response.DeviceDetailsDto;
import com.device.store.response.DeviceSummaryDto;
import com.device.store.service.DeviceService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class DeviceFacade {

    private final DeviceService deviceService;

    public DeviceDetailsDto getDeviceDetails(long externalId){
        return deviceService.getDeviceDetails(externalId);
    }

    public List<DeviceSummaryDto> getAllDevices() {
        return deviceService.getAllDevicesDetails();
    }

}
