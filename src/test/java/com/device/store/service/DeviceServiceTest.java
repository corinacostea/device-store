package com.device.store.service;

import com.device.store.mapper.DeviceMapper;
import com.device.store.model.Device;
import com.device.store.repository.DeviceRepository;
import com.device.store.request.PriceRequest;
import com.device.store.response.DeviceDetailsDto;
import com.device.store.response.DeviceDetailsUpdateDto;
import com.device.store.response.DeviceSummaryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void GIVEN_externalId_WHEN_getDevice_THEN_return_Device() {
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
    void GIVEN_noParam_WHEN_getAllDevicesDetails_THEN_return_DeviceSummaryDtoList() {
        when(deviceRepository.findAll()).thenReturn(Collections.singletonList(getDevice()));
        when(deviceMapper.getDeviceSummary(getDevice())).thenReturn(getDeviceSummaryDto());

        List<DeviceSummaryDto> response = deviceService.getAllDevicesDetails();

        assertNotNull(response);
        assertEquals("iPhone 15PRO", response.get(0).getName());
        assertEquals(4999.50, response.get(0).getFinalPrice());
        assertEquals(8599.50, response.get(0).getReferencePrice());
        assertEquals("asdfghjkl.png", response.get(0).getThumbnail());
        verify(deviceRepository).findAll();
        verify(deviceMapper).getDeviceSummary(getDevice());
        verifyNoMoreInteractions(deviceRepository, deviceMapper);
    }

    @Test
    void GIVEN_externalId_WHEN_getDeviceDetails_THEN_return_DeviceDetailsDto() {
        when(deviceRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.ofNullable(getDevice()));
        when(deviceMapper.getDeviceDetails(getDevice())).thenReturn(getDeviceDetailsDto());

        DeviceDetailsDto response = deviceService.getDeviceDetails(EXTERNAL_ID);

        assertNotNull(response);
        assertEquals(Device.Category.PHONES.name(), response.getCategory());
        assertEquals(EXTERNAL_ID, response.getExternalId());
        assertEquals("iPhone 15PRO", response.getName());
        assertEquals("eSim, black", response.getDetails());
        assertEquals(4999.50, response.getFinalPrice());
        assertEquals(8599.50, response.getReferencePrice());
        assertEquals(5, response.getStockNumber());
        assertEquals("asdfghjkl.png", response.getThumbnail());
        verify(deviceRepository).findByExternalId(EXTERNAL_ID);
        verify(deviceMapper).getDeviceDetails(getDevice());
        verifyNoMoreInteractions(deviceRepository, deviceMapper);
    }

    @Test
    void GIVEN_DeviceDetailsDto_WHEN_addDevice_THEN_ok() {
        when(deviceRepository.save(any(Device.class))).thenReturn(getDevice());
        when(deviceMapper.getDevice(getDeviceDetailsDto())).thenReturn(getDevice());

        deviceService.addDevice(getDeviceDetailsDto());

        verify(deviceRepository).save(any(Device.class));
    }

    @Test
    void GIVEN_DeviceDetailsDto_WHEN_addDevice_THEN_throw_exception() {
        Exception expectedEx = assertThrows(RuntimeException.class, () ->
                deviceService.addDevice(null));
        assertEquals("Device details request is null.", expectedEx.getMessage());
    }

    @Test
    void GIVEN_existing_externalId_WHEN_addDevice_THEN_throw_exception() {
        when(deviceRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.ofNullable(getDevice()));

        Exception expectedEx = assertThrows(RuntimeException.class, () ->
                deviceService.addDevice(getDeviceDetailsDto()));
        assertEquals("Device with id 1234 exists! Please update the device!", expectedEx.getMessage());
    }

    @Test
    void GGIVEN_DeviceDetailsUpdateDto_and_externalId_WHEN_updateDevice_THEN_ok() {
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
    void GIVEN_DeviceDetailsUpdateDto_and_externalId_WHEN_updateDevice_THEN_throwException() {
        Exception expectedEx = assertThrows(RuntimeException.class, () ->
                deviceService.updateDevice(EXTERNAL_ID, null));
        assertEquals("Could not update device because device details is missing. Details: null", expectedEx.getMessage());
    }

    @Test
    void GIVEN_PriceRequest_WHEN_changePrice_THEN_ok() {
        Long externalId = 1234L;
        Double finalPrice = 7999.50;
        Double referencePrice = 8599.50;
        PriceRequest priceRequest = PriceRequest.builder()
                .finalPrice(JsonNullable.of(finalPrice))
                .referencePrice(JsonNullable.of(referencePrice))
                .build();
        Device savedDevice = getDevice();
        savedDevice.setFinalPrice(finalPrice);
        savedDevice.setReferencePrice(referencePrice);
        when(deviceRepository.findByExternalId(externalId)).thenReturn(Optional.ofNullable(getDevice()));
        when(deviceMapper.applyPatchToEntity(priceRequest, getDevice())).thenReturn(savedDevice);

        deviceService.changePrice(externalId, priceRequest);

        verify(deviceRepository).findByExternalId(externalId);
        verify(deviceRepository).save(savedDevice);

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

    private DeviceSummaryDto getDeviceSummaryDto() {
        return DeviceSummaryDto.builder()
                .name("iPhone 15PRO")
                .finalPrice(4999.50)
                .referencePrice(8599.50)
                .thumbnail("asdfghjkl.png")
                .build();
    }
}