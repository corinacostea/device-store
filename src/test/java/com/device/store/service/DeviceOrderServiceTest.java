package com.device.store.service;

import com.device.store.mapper.DeviceMapper;
import com.device.store.mapper.DeviceStoreMapper;
import com.device.store.model.Device;
import com.device.store.model.DeviceOrder;
import com.device.store.properties.DeviceProperties;
import com.device.store.repository.DeviceOrderRepository;
import com.device.store.repository.DeviceRepository;
import com.device.store.request.DeviceaBuyRequest;
import com.device.store.response.OrderDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceOrderServiceTest {

    @Mock
    private DeviceOrderRepository deviceOrderRepository;

    @Mock
    private DeviceStoreMapper deviceStoreMapper;

    @Mock
    private DeviceService deviceService;

    @Mock
    private DeviceProperties deviceProperties;

    @InjectMocks
    private DeviceOrderService deviceOrderService;

    @BeforeEach
    void setMockOutput() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void GIVEN_OrderDetailsDto_WHEN_buyDevice_THEN_return_OrderDetailsDto() {
        long externalId = 123L;
        when(deviceService.getDevice(externalId)).thenReturn(getDevice());
        when(deviceStoreMapper.getInitialDeviceOrder(getDevicePayRequest())).thenReturn(getDeviceOrder());
        when(deviceStoreMapper.getOrderDetailsDto(any(DeviceOrder.class))).thenReturn(getOrderDetailsDto());
        when(deviceOrderRepository.save(any(DeviceOrder.class))).thenReturn(getDeviceOrder());

        OrderDetailsDto response = deviceOrderService.buyDevice(getDevicePayRequest());

        assertNotNull(response);
        verify(deviceService).getDevice(externalId);
        verify(deviceOrderRepository).save(any(DeviceOrder.class));
        verify(deviceStoreMapper).getInitialDeviceOrder(getDevicePayRequest());
        verify(deviceStoreMapper).getOrderDetailsDto(any(DeviceOrder.class));
        verifyNoMoreInteractions(deviceService, deviceOrderRepository, deviceStoreMapper);

    }

    private Device getDevice() {
        return Device.builder()
                .externalId(1234L)
                .name("iPhone 15PRO")
                .category(Device.Category.PHONES)
                .information("eSim, black")
                .finalPrice(4999.50)
                .referencePrice(8599.50)
                .thumbnail("asdfghjkl.png")
                .stockNumber(5)
                .build();
    }

    private DeviceOrder getDeviceOrder() {
        return DeviceOrder.builder()
                .customerId(321L)
                .deliveryAddress("Str.Test")
                .deliveryDetails("interfon test")
                .deliveryPerson("test")
                .deliveryPhone("0799999999")
                .build();
    }

    private DeviceaBuyRequest getDevicePayRequest() {
        return DeviceaBuyRequest.builder()
                .externalId(123L)
                .customerId(321L)
                .deliveryAddress("Str.Test")
                .deliveryDetails("interfon test")
                .deliveryPerson("test")
                .deliveryPhone("0799999999")
                .build();
    }

    private OrderDetailsDto getOrderDetailsDto() {
        return OrderDetailsDto.builder()
                .name("iPhone")
                .details("eSim")
                .deviceExternalId(1234L)
                .finalPrice(4999.99)
                .deliveryAddress("Str.Test")
                .deliveryDetails("interfon test")
                .deliveryPerson("test")
                .deliveryPhone("07999999999")
                .build();
    }
}