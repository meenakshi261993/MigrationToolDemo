package com.migration.demo.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.migration.demo.mappingRule.MappingRules;
import com.migration.demo.util.AppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class RuleParser {

    private static final Logger logger = LoggerFactory.getLogger(RuleParser.class);

    /**
     * Parse the rules received from the user based on defined template
     *
     * @param json mapping rules in json format
     * @return List<MappingRules> list of mapping rules
     */
    public List<MappingRules> parseJson(String json) {

        List<MappingRules> rules = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode ruleJsonNode = mapper.readTree(json);
            JsonNode ruleArray = ruleJsonNode.get(AppConstant.RULES);
            if(ruleArray.isArray()){
                Iterator<JsonNode> ruleIterator = ruleArray.iterator();
                while (ruleIterator.hasNext()){
                    JsonNode rule = ruleIterator.next();
                    MappingRules ruleObjects = mapper.convertValue(rule, MappingRules.class);
                    rules.add(ruleObjects);
                }
}
        } catch (JsonProcessingException e) {
            logger.error("Error occurred while parsing the mapping rules " +e.getMessage());
        }
        return rules;
    }
}
