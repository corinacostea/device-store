package com.device.store.service;

import com.device.store.mapper.DeviceMapper;
import com.device.store.model.Device;
import com.device.store.repository.DeviceRepository;
import com.device.store.response.DeviceDetailsDto;
import com.device.store.response.DeviceDetailsUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeviceServiceTest {


    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceMapper deviceMapper;

    @InjectMocks
    private DeviceService deviceService;

    @BeforeEach
    void setMockOutput() {
        MockitoAnnotations.openMocks(this);
    }

    private static final Long EXTERNAL_ID = 1234L;

    @Test
    void GUVEN_externalId_WHEN_getDevice_THEN_return_Device() {
        when(deviceRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.ofNullable(getDevice()));

        Device response = deviceService.getDevice(EXTERNAL_ID);

        assertNotNull(response);
        assertEquals(Device.Category.PHONES.name(), response.getCategory().name());
        assertEquals(EXTERNAL_ID, response.getExternalId());
        assertEquals("iPhone 15PRO", response.getName());
        assertEquals("eSim, black", response.getInformation());
        assertEquals(4999.50, response.getFinalPrice());
        assertEquals(8599.50, response.getReferencePrice());
        assertEquals(5, response.getStockNumber());
        assertEquals("asdfghjkl.png", response.getThumbnail());
        verify(deviceRepository).findByExternalId(EXTERNAL_ID);
    }

    @Test
    void GUVEN_DeviceDetailsDto_WHEN_addDevice_THEN_ok() {
        when(deviceRepository.save(any(Device.class))).thenReturn(getDevice());
        when(deviceMapper.getDevice(getDeviceDetailsDto())).thenReturn(getDevice());

        deviceService.addDevice(getDeviceDetailsDto());

        verify(deviceRepository).save(any(Device.class));
    }

    @Test
    void GUVEN_DeviceDetailsDto_WHEN_addDevice_THEN_throw_exception() {
        Exception expectedEx = assertThrows(RuntimeException.class, () ->
                deviceService.addDevice(null));
        assertEquals("Device details request is null.", expectedEx.getMessage());
    }

    @Test
    void GUVEN_existing_externalId_WHEN_addDevice_THEN_throw_exception() {
        when(deviceRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.ofNullable(getDevice()));

        Exception expectedEx = assertThrows(RuntimeException.class, () ->
                deviceService.addDevice(getDeviceDetailsDto()));
        assertEquals("Device with id 1234 exists! Please update the device!", expectedEx.getMessage());
    }

    @Test
    void GUVEN_DeviceDetailsUpdateDto_and_externalId_WHEN_updateDevice_THEN_ok() {
        Device device = getDevice();
        device.setStockNumber(1);
        device.setStock(Device.Stock.LIMITED_STOCK);
        DeviceDetailsUpdateDto deviceDetailsDto = DeviceDetailsUpdateDto.builder()
                .name("iPhone 15PRO")
                .details("eSim, black")
                .finalPrice(4999.50)
                .referencePrice(8599.50)
                .thumbnail("asdfghjkl.png")
                .stockNumber(1)
                .build();
        when(deviceRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.ofNullable(getDevice()));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        deviceService.updateDevice(EXTERNAL_ID, deviceDetailsDto);

        verify(deviceRepository).findByExternalId(EXTERNAL_ID);
        verify(deviceRepository).save(device);
    }

    @Test
    void GUVEN_DeviceDetailsUpdateDto_and_externalId_WHEN_updateDevice_THEN_throwException() {
        Long externalId = 1234L;
        Exception expectedEx = assertThrows(RuntimeException.class, () ->
                deviceService.updateDevice(externalId, null));
        assertEquals("Could not update device because device details is missing. Details: null", expectedEx.getMessage());
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
}