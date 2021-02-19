package com.migration.demo.deviceModels;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds the input model data
 *
 */
@Component
@Getter
@Setter
public class DeviceModel {

    private Map<String,String> inputData = new HashMap<>();

}
