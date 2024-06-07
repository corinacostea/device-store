package com.device.store.service;

import com.device.store.exception.NotFoundException;
import com.device.store.exception.PreconditionFailedException;
import com.device.store.mapper.DeviceMapper;
import com.device.store.model.Device;
import com.device.store.repository.DeviceRepository;
import com.device.store.request.PriceRequest;
import com.device.store.response.DeviceDetailsDto;
import com.device.store.response.DeviceDetailsUpdateDto;
import com.device.store.response.DeviceSummaryDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public Device getDevice(long externalId) {
        return deviceRepository.findByExternalId(externalId)
                .orElseThrow(() -> new NotFoundException("Device with id " + externalId + " not found!"));
    }

    public DeviceDetailsDto getDeviceDetails(long externalId) {
        return deviceMapper.getDeviceDetails(getDevice(externalId));
    }

    public List<DeviceSummaryDto> getAllDevicesDetails() {
        return getAllDevices()
                .stream()
                .sorted(Comparator.comparing(Device::getCreationDate).reversed())
                .map(deviceMapper::getDeviceSummary)
                .toList();
    }

    @Transactional
    public void addDevice(DeviceDetailsDto deviceDetailsDto) {
        Validate.notNull(deviceDetailsDto, "Device details request is null.");
        if (getOptionalDevice(deviceDetailsDto.getExternalId()).isPresent()) {
            log.debug("DeviceDetailsDto details is: {}", deviceDetailsDto);
            throw new RuntimeException("Device with id " + deviceDetailsDto.getExternalId() + " exists! Please update the device!");
        }
        Device device = deviceMapper.getDevice(deviceDetailsDto);
        device.setStock(getStock(device.getStockNumber()));
        deviceRepository.save(device);
    }

    @Transactional
    public void updateDevice(long externalId, DeviceDetailsUpdateDto deviceDetailsUpdateDto) {
        log.debug("DeviceDetailsUpdateDto details is: {}", deviceDetailsUpdateDto);
        if (deviceDetailsUpdateDto == null) {
            throw new PreconditionFailedException(
                    "Could not update device because device details is missing. Details: " + deviceDetailsUpdateDto);
        }
        Device device = getDevice(externalId);
        enrichWithUpdatedDetails(device, deviceDetailsUpdateDto);
        device.setStock(getStock(device.getStockNumber()));
        deviceRepository.save(device);
    }

    @Transactional
    public void changePrice(long externalId, PriceRequest priceRequest) {
        Device device = getDevice(externalId);
        if (priceRequest.getFinalPrice().get() == 0 || priceRequest.getFinalPrice().get() == 0) {
            throw new PreconditionFailedException(
                    "Could not update device price to 0");
        }
        Device patchedDevice = deviceMapper.applyPatchToEntity(priceRequest, device);
        patchedDevice.setStock(getStock(patchedDevice.getStockNumber()));
        deviceRepository.save(patchedDevice);
    }

    private void enrichWithUpdatedDetails(Device device, DeviceDetailsUpdateDto deviceDetailsDto) {
        device.setName(deviceDetailsDto.getName());
        device.setInformation(deviceDetailsDto.getDetails());
        device.setFinalPrice(deviceDetailsDto.getFinalPrice());
        device.setReferencePrice(deviceDetailsDto.getReferencePrice());
        device.setThumbnail(deviceDetailsDto.getThumbnail());
        device.setStockNumber(deviceDetailsDto.getStockNumber());
    }

    private Optional<Device> getOptionalDevice(long externalId) {
        return deviceRepository.findByExternalId(externalId);
    }

    private List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    private Device.Stock getStock(Integer stockNumber) {
        return switch (stockNumber) {
            case 0 -> Device.Stock.OUT_OF_STOCK;
            case 1 -> Device.Stock.LIMITED_STOCK;
            case 2 -> Device.Stock.LIMITED_STOCK;
            case 3 -> Device.Stock.LIMITED_STOCK;
            default -> Device.Stock.IN_STOCK;
        };
    }

}
