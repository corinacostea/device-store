package com.device.store.properties;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.device")
@Data
@Accessors(chain = true)
public class DeviceProperties {

    private Integer reservationHours;

}
