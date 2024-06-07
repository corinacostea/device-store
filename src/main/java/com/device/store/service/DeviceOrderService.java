package com.device.store.service;

import com.device.store.mapper.DeviceStoreMapper;
import com.device.store.model.Device;
import com.device.store.model.DeviceOrder;
import com.device.store.repository.DeviceOrderRepository;
import com.device.store.request.DevicePayRequest;
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

    public OrderDetailsDto buyDevice(DevicePayRequest devicePayRequest) {
        Device device = deviceService.getDevice(devicePayRequest.getExternalId());
        DeviceOrder deviceOrder = deviceStoreMapper.getInitialDeviceOrder(devicePayRequest);
        deviceOrder.setDevice(device);
        deviceOrder.setStatus(DeviceOrder.Status.WAITING_FOR_PAYMENT);
        deviceOrder.setReservationTime(LocalDateTime.now().plusHours(12));
        deviceOrderRepository.save(deviceOrder);
        return deviceStoreMapper.getOrderDetailsDto(deviceOrder);
    }
    
}
