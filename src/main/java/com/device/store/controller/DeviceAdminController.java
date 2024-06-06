package com.device.store.controller;

import com.device.store.facade.DeviceAdminFacade;
import com.device.store.response.DeviceDetailsDto;
import com.device.store.response.DeviceDetailsUpdateDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin/device")
public class DeviceAdminController {

    private final DeviceAdminFacade deviceAdminFacade;

    @PostMapping(value = "/add",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addDevice(@RequestBody DeviceDetailsDto deviceDetailsDto) {
        deviceAdminFacade.addDevice(deviceDetailsDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/update/{externalId}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateDevice(@PathVariable(name = "externalId") long externalId,
                                             @RequestBody DeviceDetailsUpdateDto deviceDetailsUpdateDto) {
        deviceAdminFacade.updateDevice(externalId, deviceDetailsUpdateDto);
        return ResponseEntity.ok().build();
    }

}
