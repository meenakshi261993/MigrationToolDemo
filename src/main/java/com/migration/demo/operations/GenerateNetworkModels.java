package com.migration.demo.operations;

import com.fasterxml.jackson.databind.JsonNode;
import com.migration.demo.deviceModels.DeviceModel;
import com.migration.demo.mappers.DeviceToNetworkMapping;
import com.migration.demo.parsers.JsonModelParser;
import com.migration.demo.parsers.KeyValueParser;
import com.migration.demo.util.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Component
public class GenerateNetworkModels {

    @Autowired
     private KeyValueParser keyValueParser;

    @Autowired
    private JsonModelParser jsonModelParser;
    
    @Autowired
    private DeviceModel deviceModel;

    @Autowired
    private DeviceToNetworkMapping deviceToNetworkMapping;

    private static final Logger logger = LoggerFactory.getLogger(GenerateNetworkModels.class);

    /**
     * Process the files from the input directory
     * @param file path of  input files
     * @return string containing the mapped network details
     * @throws IOException
     */
    public String generateNetwork(String file) throws IOException {
        List<JsonNode> networkNode=null;
        String content = new String(Files.readAllBytes(Paths.get(file)));
        logger.debug("Processing the input device model file " +Paths.get(file).getFileName().toString());
         if(Paths.get(file).getFileName().toString().endsWith(AppConstant.JSON)){
             networkNode = getNetworkFromInputJsonModel(content);

         }else if(Paths.get(file).getFileName().toString().endsWith(AppConstant.TXT)){
             networkNode = getNetworkFromInputKeyValueModel(content);
         }

        return networkNode.toString();
    }

    /**
     * Maps the keyValue model to the map for processing further
     *
     * @param content  input text file content
     * @return List<JsonNode> mapped network list for the given input
     * @throws IOException
     */
    private List<JsonNode> getNetworkFromInputKeyValueModel(String content) throws IOException {
        List<JsonNode> networkNode;
        keyValueParser.parseInputText(content);
        Map<String, String> inputModel = deviceModel.getInputData();
        networkNode = deviceToNetworkMapping.mapDeviceModelToNetwork(inputModel, AppConstant.KEY_VALUE);
        return networkNode;
    }

    /**
     * Maps the JSON  model to the map for processing further
     *
     * @param content input JSON file content
     * @return List<JsonNode> mapped network list for the given input
     */
    private List<JsonNode> getNetworkFromInputJsonModel(String content) {
        List<JsonNode> networkNode;
        jsonModelParser.parseInputJson(content);
        Map<String, String> inputModel = deviceModel.getInputData();
        networkNode = deviceToNetworkMapping.mapDeviceModelToNetwork(inputModel, AppConstant.JSON_VALUE);
        return networkNode;
    }

}
