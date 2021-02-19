package com.migration.demo.parsers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.migration.demo.deviceModels.DeviceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JsonModelParser {

    @Autowired
    DeviceModel deviceModel;

    private static final Logger logger = LoggerFactory.getLogger(JsonModelParser.class);

    /**
     * Parse the input JSON file content to map into the hashmap
     *
     * @param json input json model file content
     */
   public void  parseInputJson(String json){

       Map<String, String> jsonMap = new HashMap<>();
       ObjectMapper jsonMapper = new ObjectMapper();
       try {
           jsonMap = jsonMapper.readValue(json, new TypeReference<Map<String,String>>(){});
       } catch (JsonProcessingException e) {
           logger.error("Error occurred while parsing the input model" +e.getMessage());
       }
     deviceModel.setInputData(jsonMap);
   }

}
