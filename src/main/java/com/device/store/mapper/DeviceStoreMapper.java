package com.device.store.mapper;

import com.device.store.model.DeviceOrder;
import com.device.store.request.DevicePayRequest;
import com.device.store.response.OrderDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class DeviceStoreMapper {

    public DeviceOrder getInitialDeviceOrder(DevicePayRequest devicePayRequest) {
        return DeviceOrder.builder()
                .customerId(devicePayRequest.getCustomerId())
                .deliveryAddress(devicePayRequest.getDeliveryAddress())
                .deliveryDetails(devicePayRequest.getDeliveryDetails())
                .deliveryPerson(devicePayRequest.getDeliveryPerson())
                .deliveryPhone(devicePayRequest.getDeliveryPhone())
                .build();
    }

    public OrderDetailsDto getOrderDetailsDto(DeviceOrder deviceOrder) {
        return OrderDetailsDto.builder()
                .name(deviceOrder.getDevice().getName())
                .details(deviceOrder.getDevice().getInformation())
                .deviceExternalId(deviceOrder.getDevice().getExternalId())
                .finalPrice(deviceOrder.getDevice().getFinalPrice())
                .deliveryAddress(deviceOrder.getDeliveryAddress())
                .deliveryDetails(deviceOrder.getDeliveryDetails())
                .deliveryPerson(deviceOrder.getDeliveryPerson())
                .deliveryPhone(deviceOrder.getDeliveryPhone())
                .build();
    }


}
