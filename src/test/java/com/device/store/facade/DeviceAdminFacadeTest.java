package com.device.store.facade;

import com.device.store.request.PriceRequest;
import com.device.store.response.DeviceDetailsDto;
import com.device.store.response.DeviceDetailsUpdateDto;
import com.device.store.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.jackson.nullable.JsonNullable;

import static org.mockito.Mockito.*;

class DeviceAdminFacadeTest {
    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceAdminFacade deviceAdminFacade;

    @BeforeEach
    void setMockOutput() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void GIVEN_DeviceDetailsDto_WHEN_addDevice_THEN_ok() {
        doNothing().when(deviceService).addDevice(getDeviceDetailsDto());

        deviceAdminFacade.addDevice(getDeviceDetailsDto());

        verify(deviceService).addDevice(getDeviceDetailsDto());
    }

    @Test
    void GIVEN_DeviceDetailsUpdateDto_and_externalId_WHEN_updateDevice_THEN_ok() {
        doNothing().when(deviceService).updateDevice(123L, getDeviceDetailsUpdateDto());

        deviceAdminFacade.updateDevice(123L, getDeviceDetailsUpdateDto());

        verify(deviceService).updateDevice(123L, getDeviceDetailsUpdateDto());

    }

    @Test
    void GIVEN_PriceRequest_and_externalId_WHEN_changePrice_THEN_ok() {
        PriceRequest priceRequest = PriceRequest.builder()
                .referencePrice(JsonNullable.of(100.00))
                .finalPrice(JsonNullable.of(100.00))
                .build();
        doNothing().when(deviceService).changePrice(123L, priceRequest);

        deviceAdminFacade.changePrice(123L, priceRequest);

        verify(deviceService).changePrice(123L, priceRequest);

    }

    private DeviceDetailsDto getDeviceDetailsDto() {
        return DeviceDetailsDto.builder()
                .externalId(1234L)
                .name("iPhone 15PRO")
                .category("PHONES")
                .details("eSim, black")
                .finalPrice(4999.50)
                .referencePrice(8599.50)
                .thumbnail("asdfghjkl.png")
                .stockNumber(5)
                .build();
    }

    private DeviceDetailsUpdateDto getDeviceDetailsUpdateDto() {
        return DeviceDetailsUpdateDto.builder()
                .name("iPhone 15PRO")
                .details("eSim, black")
                .finalPrice(4999.50)
                .referencePrice(8599.50)
                .thumbnail("asdfghjkl.png")
                .stockNumber(5)
                .build();
    }
}