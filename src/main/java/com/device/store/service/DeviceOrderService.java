package com.device.store.service;

import com.device.store.exception.NotFoundException;
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

    public String payDevice(long orderId) {
        DeviceOrder deviceOrder = getDeviceOrder(orderId);
        checkReservationTime(deviceOrder);
        return mockPaymentReturnUrlStatus(deviceOrder);
    }

    private void checkReservationTime(DeviceOrder deviceOrder) {
        if (deviceOrder.getReservationTime().isBefore(LocalDateTime.now())) {
            deviceOrderRepository.delete(deviceOrder);
            throw new RuntimeException("Order has expired!");
        }
    }

    private DeviceOrder getDeviceOrder(long id) {
        return deviceOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order with id " + id + " not found!"));
    }

    private String mockPaymentReturnUrlStatus(DeviceOrder deviceOrder) {
        double transactionId = Math.random();
        deviceOrder.setMerchantTransactionId(String.valueOf(transactionId));
        if (transactionId % 2 == 0) {
            deviceOrder.setStatus(DeviceOrder.Status.ORDER_PLACED);
        } else {
            deviceOrder.setStatus(DeviceOrder.Status.ORDER_FAILED);
        }
        deviceOrderRepository.save(deviceOrder);
        return deviceOrder.getStatus().name();
    }
    
}
