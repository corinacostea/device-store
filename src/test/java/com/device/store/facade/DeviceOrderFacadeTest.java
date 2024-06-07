package com.device.store.facade;

import com.device.store.request.DeviceaBuyRequest;
import com.device.store.response.DeviceDetailsDto;
import com.device.store.response.OrderDetailsDto;
import com.device.store.service.DeviceOrderService;
import com.device.store.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceOrderFacadeTest {
    @Mock
    private DeviceOrderService deviceOrderService;

    @InjectMocks
    private DeviceOrderFacade deviceOrderFacade;

    @BeforeEach
    void setMockOutput() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void GIVEN_OrderDetailsDto_WHEN_buyDevice_THEN_ok() {
        when(deviceOrderService.buyDevice(getDevicePayRequest())).thenReturn(getOrderDetailsDto());

        OrderDetailsDto orderDetailsDto = deviceOrderFacade.buyDevice(getDevicePayRequest());

        assertNotNull(orderDetailsDto);
        verify(deviceOrderService).buyDevice(getDevicePayRequest());
        verifyNoMoreInteractions(deviceOrderService);
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
}