package com.migration.demo.parsers;

import com.migration.demo.deviceModels.DeviceModel;
import com.migration.demo.util.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class KeyValueParser {

    @Autowired
    DeviceModel deviceModel;

    private static final Logger logger = LoggerFactory.getLogger(KeyValueParser.class);

    /**
     *Parse the input text file content to map into the hashmap
     *
     * @param text input txt model content
     * @throws IOException
     */
    public void parseInputText(String text) throws IOException {
        Map<String, String> textMap = new HashMap<>();
        String line = null;
        try(BufferedReader reader = new BufferedReader(new StringReader(text))){
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(AppConstant.REGEX);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if (!key.equals("") && !value.equals("")) {
                        textMap.put(key, value);
                    }
                }
                else{
                    logger.error("Ignoring line: " + line);
                }
            }
        }
        catch (Exception e){
            logger.error("Error occurred during the file reading" +e.getMessage());
    }
        deviceModel.setInputData(textMap);

    }
}
