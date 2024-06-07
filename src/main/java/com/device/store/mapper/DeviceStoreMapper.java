package com.device.store.mapper;

import com.device.store.model.DeviceOrder;
import com.device.store.request.DeviceaBuyRequest;
import com.device.store.response.OrderDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class DeviceStoreMapper {

    public DeviceOrder getInitialDeviceOrder(DeviceaBuyRequest deviceaBuyRequest) {
        return DeviceOrder.builder()
                .customerId(deviceaBuyRequest.getCustomerId())
                .deliveryAddress(deviceaBuyRequest.getDeliveryAddress())
                .deliveryDetails(deviceaBuyRequest.getDeliveryDetails())
                .deliveryPerson(deviceaBuyRequest.getDeliveryPerson())
                .deliveryPhone(deviceaBuyRequest.getDeliveryPhone())
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
