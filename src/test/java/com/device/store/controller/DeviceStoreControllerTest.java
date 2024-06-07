package com.device.store.controller;

import com.device.store.DeviceStoreApplication;
import com.device.store.facade.DeviceFacade;
import com.device.store.facade.DeviceOrderFacade;
import com.device.store.request.DeviceaBuyRequest;
import com.device.store.response.DeviceDetailsDto;
import com.device.store.response.DeviceSummaryDto;
import com.device.store.response.OrderDetailsDto;
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

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DeviceStoreApplication.class, webEnvironment = RANDOM_PORT)
class DeviceStoreControllerTest {
    @MockBean
    private DeviceFacade deviceFacade;
    @MockBean
    private DeviceOrderFacade deviceOrderFacade;

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    private static final long EXTERNAL_ID = 123;
    private static final String BASE_API = "/api/v1/device";

    @Test
    void GIVEN_externalId_WHEN_getProductDetails_THEN_ok() throws Exception {
        when(deviceFacade.getDeviceDetails(EXTERNAL_ID)).thenReturn(getDeviceDetailsDto());
        mvc.perform(get(BASE_API + "/details/{externalId}", EXTERNAL_ID)
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
                .andExpect(status().isOk());

        verify(deviceFacade).getDeviceDetails(EXTERNAL_ID);
    }

    @Test
    void GIVEN_emptyParam_WHEN_getAllDevices_THEN_ok() throws Exception {
        DeviceSummaryDto deviceSummaryDto = getDeviceSummaryDto();
        when(deviceFacade.getAllDevices()).thenReturn(Collections.singletonList(deviceSummaryDto));
        mvc.perform(get(BASE_API + "/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].name", is(deviceSummaryDto.getName())))
                .andExpect(jsonPath("$[0].finalPrice", is(deviceSummaryDto.getFinalPrice())))
                .andExpect(jsonPath("$[0].referencePrice", is(deviceSummaryDto.getReferencePrice())))
                .andExpect(jsonPath("$[0].thumbnail", is(deviceSummaryDto.getThumbnail())));

        verify(deviceFacade).getAllDevices();
    }

    @Test
    void GIVEN_DevicePayRequest_WHEN_buyDevice_THEN_ok() throws Exception {
        OrderDetailsDto order = getOrderDetailsDto();
        when(deviceOrderFacade.buyDevice(getDevicePayRequest())).thenReturn(order);

        mvc.perform(get(BASE_API + "/buy")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"externalId\": \"123\"," +
                                "\"customerId\": \"321\"," +
                                "\"deliveryAddress\": \"Str.Test\"," +
                                "\"deliveryPerson\": \"test\"," +
                                "\"deliveryPhone\": \"0799999999\"," +
                                "\"deliveryDetails\": \"interfon test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is(order.getName())))
                .andExpect(jsonPath("$.details", is(order.getDetails())))
                .andExpect(jsonPath("$.finalPrice", is(order.getFinalPrice())))
                .andExpect(jsonPath("$.deviceExternalId", is(Math.toIntExact(order.getDeviceExternalId()))))
                .andExpect(jsonPath("$.deliveryAddress", is(order.getDeliveryAddress())))
                .andExpect(jsonPath("$.deliveryPerson", is(order.getDeliveryPerson())))
                .andExpect(jsonPath("$.deliveryPhone", is(order.getDeliveryPhone())))
                .andExpect(jsonPath("$.deliveryDetails", is(order.getDeliveryDetails())));

        verify(deviceOrderFacade).buyDevice(getDevicePayRequest());
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

    private DeviceaBuyRequest getDevicePayRequest() {
        return DeviceaBuyRequest.builder()
                .externalId(EXTERNAL_ID)
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