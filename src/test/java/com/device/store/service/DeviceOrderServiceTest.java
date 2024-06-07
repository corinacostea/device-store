package com.device.store.service;

import com.device.store.mapper.DeviceStoreMapper;
import com.device.store.model.Device;
import com.device.store.model.DeviceOrder;
import com.device.store.properties.DeviceProperties;
import com.device.store.repository.DeviceOrderRepository;
import com.device.store.request.DeviceBuyRequest;
import com.device.store.response.OrderDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Test
    void GIVEN_orderId_WHEN_payDevice_THEN_return_status() {
        long orderId = 123L;
        DeviceOrder deviceOrder = getDeviceOrder();
        deviceOrder.setDevice(getDevice());
        deviceOrder.setReservationTime(LocalDateTime.now().plusHours(10));
        when(deviceOrderRepository.findById(orderId)).thenReturn(Optional.ofNullable(deviceOrder));
        when(deviceOrderRepository.save(any(DeviceOrder.class))).thenReturn(deviceOrder);

        String response = deviceOrderService.payDevice(orderId);

        assertNotNull(response);
        verify(deviceOrderRepository).findById(anyLong());
        verify(deviceOrderRepository).save(any(DeviceOrder.class));
        verifyNoMoreInteractions(deviceService, deviceOrderRepository, deviceStoreMapper);
    }

    @Test
    void GIVEN_orderId_WHEN_payDevice_THEN_throw_ex() {
        long orderId = 123L;
        DeviceOrder deviceOrder = getDeviceOrder();
        deviceOrder.setReservationTime(LocalDateTime.now().minusHours(10));
        when(deviceOrderRepository.findById(orderId)).thenReturn(Optional.ofNullable(deviceOrder));

        Exception expectedEx = assertThrows(RuntimeException.class, () ->
                deviceOrderService.payDevice(orderId));
        assertEquals("Order has expired at: " + deviceOrder.getReservationTime(), expectedEx.getMessage());

        verify(deviceOrderRepository).findById(anyLong());
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

    private DeviceBuyRequest getDevicePayRequest() {
        return DeviceBuyRequest.builder()
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