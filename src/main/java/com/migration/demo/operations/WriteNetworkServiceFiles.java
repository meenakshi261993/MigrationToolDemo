package com.migration.demo.operations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.migration.demo.util.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class WriteNetworkServiceFiles {

    private static final String HEADER_FILE_NAME = "file_name";
    private static final Logger logger = LoggerFactory.getLogger(WriteNetworkServiceFiles.class);

    /**
     * Parse the output received after the transformation
     *
     * @param msg
     */
    public void process(Message<String> msg) {
        String fileName = (String) msg.getHeaders().get(HEADER_FILE_NAME);
        String content = msg.getPayload();
        try {
            createNetworkServiceFiles(content ,fileName);
        } catch (IOException e) {
            logger.error("Error while creating the output network service file " +e.getMessage());
        }
    }

    /**
     * Maps the received network details from string to JsonNode
     *
     * @param content string content to be written into file
     * @param fileName string input filename
     * @throws IOException
     */
    private void createNetworkServiceFiles(String content,String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode networkJsonNode=null;
        try {
            networkJsonNode = mapper.readTree(content);
        } catch (JsonProcessingException e) {
            logger.error("Error occurred while parsing the mapped network JSON node" +e.getMessage());
        }
        writeNetworkFilesToOutput(fileName, networkJsonNode);
    }

    /**
     *It writes the network JsonNode to output yaml file
     *
     * @param fileName  string input filename
     * @param networkJsonNode updtated network JsonNode
     * @throws IOException
     */
    private void writeNetworkFilesToOutput(String fileName, JsonNode networkJsonNode) throws IOException {
        String networkFilename;
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        fileName = fileName.split(Pattern.quote(AppConstant.DOT))[0];
        if(networkJsonNode.isArray()){
            for (JsonNode networkNode : networkJsonNode){
                networkFilename = fileName + "_service" + networkNode.get("network_service_type").asText() + ".yaml";
                logger.debug("Generated network service file " +networkFilename);
                File outputFile = new File("output"+networkFilename);
                om.writeValue(outputFile,networkNode);
            }
        }
    }
}
