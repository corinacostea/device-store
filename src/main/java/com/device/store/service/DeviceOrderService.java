package com.device.store.service;

import com.device.store.exception.NotFoundException;
import com.device.store.mapper.DeviceStoreMapper;
import com.device.store.model.Device;
import com.device.store.model.DeviceOrder;
import com.device.store.properties.DeviceProperties;
import com.device.store.repository.DeviceOrderRepository;
import com.device.store.request.DeviceBuyRequest;
import com.device.store.response.OrderDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceOrderService {

    private final DeviceService deviceService;
    private final DeviceStoreMapper deviceStoreMapper;
    private final DeviceOrderRepository deviceOrderRepository;
    private final DeviceProperties deviceProperties;

    public OrderDetailsDto buyDevice(DeviceBuyRequest deviceBuyRequest) {
        Device device = deviceService.getDevice(deviceBuyRequest.getExternalId());
        DeviceOrder deviceOrder = deviceStoreMapper.getInitialDeviceOrder(deviceBuyRequest);
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
            throw new RuntimeException("Order has expired at: " + deviceOrder.getReservationTime());
        }
    }

    private DeviceOrder getDeviceOrder(long id) {
        return deviceOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order with id " + id + " not found!"));
    }

    private String mockPaymentReturnUrlStatus(DeviceOrder deviceOrder) {
        Random rand = new Random();
        Integer transactionId = rand.nextInt(100 - 1 + 1) + 1;
        log.info("transactionId is {}", transactionId);
        deviceOrder.setMerchantTransactionId(String.valueOf(transactionId));
        if (transactionId % 2 == 0) {
            deviceOrder.setStatus(DeviceOrder.Status.ORDER_PLACED);
            deviceOrder.setDeliveryDate(LocalDateTime.now().plusDays(deviceProperties.getDeliveryDays()));
            deviceOrder.setDeliveryPrice(deviceProperties.getDeliveryPrice());
        } else {
            deviceOrder.setStatus(DeviceOrder.Status.ORDER_FAILED);
        }
        deviceOrder.getDevice().setStockNumber(deviceOrder.getDevice().getStockNumber() - 1);
        deviceOrderRepository.save(deviceOrder);
        return deviceOrder.getStatus().name();
    }
    
}
