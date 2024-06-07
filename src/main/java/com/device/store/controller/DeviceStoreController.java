package com.device.store.controller;

import com.device.store.facade.DeviceFacade;
import com.device.store.facade.DeviceOrderFacade;
import com.device.store.request.DeviceaBuyRequest;
import com.device.store.response.DeviceDetailsDto;
import com.device.store.response.DeviceSummaryDto;
import com.device.store.response.OrderDetailsDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/device")
public class DeviceStoreController {

    private final DeviceFacade deviceFacade;
    private final DeviceOrderFacade deviceOrderFacade;

    @GetMapping(value = "/details/{externalId}",
                produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceDetailsDto> getProductDetails(@PathVariable(name = "externalId") long externalId) {
        return ResponseEntity.ok(deviceFacade.getDeviceDetails(externalId));
    }

    @GetMapping(value = "/all",
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DeviceSummaryDto>> getAllDevices() {
        return ResponseEntity.ok(deviceFacade.getAllDevices());
    }

    @GetMapping(value = "/buy",
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDetailsDto> buyDevice(@RequestBody DeviceaBuyRequest deviceaBuyRequest) {
        return ResponseEntity.ok(deviceOrderFacade.buyDevice(deviceaBuyRequest));
    }

    @GetMapping(value = "/pay/{orderId}",
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> payDevice(@PathVariable(name = "orderId") long orderId) {
        return ResponseEntity.ok(deviceOrderFacade.payDevice(orderId));
    }

}
