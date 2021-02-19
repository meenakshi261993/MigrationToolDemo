package com.migration.demo.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import java.io.File;


@Configuration
public class ReadDeviceModels {

    @Autowired
    private GenerateNetworkModels generateNetworkModels;

    @Autowired
    private WriteNetworkServiceFiles writeNetworkServiceFiles;

    @Bean
    public IntegrationFlow fileReadingAndWritting(){
        return IntegrationFlows.from(fileReaderFromInput(),spec -> spec.poller(Pollers.fixedDelay(100)))
                .transform(generateNetworkModels,"generateNetwork")
                .handle(writeNetworkServiceFiles,"process").get();
    }

    @Bean
    public FileReadingMessageSource fileReaderFromInput(){
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File("input"));
        return source;
    }
}
