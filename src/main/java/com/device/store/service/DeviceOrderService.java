package com.device.store.service;

import com.device.store.mapper.DeviceStoreMapper;
import com.device.store.model.Device;
import com.device.store.model.DeviceOrder;
import com.device.store.properties.DeviceProperties;
import com.device.store.repository.DeviceOrderRepository;
import com.device.store.request.DeviceaBuyRequest;
import com.device.store.response.OrderDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeviceOrderService {

    private final DeviceService deviceService;
    private final DeviceStoreMapper deviceStoreMapper;
    private final DeviceOrderRepository deviceOrderRepository;
    private final DeviceProperties deviceProperties;

    public OrderDetailsDto buyDevice(DeviceaBuyRequest deviceaBuyRequest) {
        Device device = deviceService.getDevice(deviceaBuyRequest.getExternalId());
        DeviceOrder deviceOrder = deviceStoreMapper.getInitialDeviceOrder(deviceaBuyRequest);
        deviceOrder.setDevice(device);
        deviceOrder.setStatus(DeviceOrder.Status.WAITING_FOR_PAYMENT);
        deviceOrder.setReservationTime(LocalDateTime.now().plusHours(deviceProperties.getReservationHours()));
        deviceOrderRepository.save(deviceOrder);
        return deviceStoreMapper.getOrderDetailsDto(deviceOrder);
    }
    
}
