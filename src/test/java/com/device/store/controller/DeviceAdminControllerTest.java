package com.device.store.controller;

import com.device.store.DeviceStoreApplication;
import com.device.store.facade.DeviceAdminFacade;
import com.device.store.request.PriceRequest;
import com.device.store.response.DeviceDetailsDto;
import com.device.store.response.DeviceDetailsUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DeviceStoreApplication.class, webEnvironment = RANDOM_PORT)
class DeviceAdminControllerTest {
    @MockBean
    protected DeviceAdminFacade deviceAdminFacade;

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    private static final String BASE_API = "/api/v1/admin/device";
    private static final String EXTERNAL_ID = "123";

    @Test
    void GIVEN_DeviceDetailsDto_WHEN_addDevice_THEN_ok() throws Exception {
        doNothing().when(deviceAdminFacade).addDevice(any(DeviceDetailsDto.class));
        mvc.perform(post(BASE_API + "/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"iPhone 15PRO\"," +
                                "\"details\": \"eSim, black\"," +
                                "\"externalId\": \"123\"," +
                                "\"finalPrice\": \"4999.50\"," +
                                "\"referencePrice\": \"8599.50\"," +
                                "\"category\": \"PHONE\"," +
                                "\"thumbnail\": \"MEMBER_GET_MEMBER_SENDER_100GB\"," +
                                "\"stockNumber\": \"10\"}"))
                .andExpect(status().isNoContent());

        verify(deviceAdminFacade).addDevice(any(DeviceDetailsDto.class));
    }

    @Test
    void GIVEN_externalId_WHEN_updateDevice_THEN_ok() throws Exception {
        doNothing().when(deviceAdminFacade).updateDevice(anyLong(), any(DeviceDetailsUpdateDto.class));
        mvc.perform(put(BASE_API + "/update/{externalId}", EXTERNAL_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"iPhone 15PRO\"," +
                                "\"details\": \"eSim, black\"," +
                                "\"finalPrice\": \"4999.50\"," +
                                "\"referencePrice\": \"8599.50\"," +
                                "\"thumbnail\": \"MEMBER_GET_MEMBER_SENDER_100GB\"," +
                                "\"stockNumber\": \"10\"}"))
                .andExpect(status().isNoContent());

        verify(deviceAdminFacade).updateDevice(anyLong(), any(DeviceDetailsUpdateDto.class));
    }

    @Test
    void GIVEN_externalId_WHEN_changePrice_THEN_ok() throws Exception {
        doNothing().when(deviceAdminFacade).updateDevice(anyLong(), any(DeviceDetailsUpdateDto.class));
        mvc.perform(patch(BASE_API + "/change-price/{externalId}", EXTERNAL_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"finalPrice\": \"4999.50\"," +
                                "\"referencePrice\": \"8599.50\"}"))
                .andExpect(status().isNoContent());

        verify(deviceAdminFacade).changePrice(anyLong(), any(PriceRequest.class));
    }
}