package com.migration.demo.mappingRule;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

/**
 * Holds  the  mapping rules defined
 *
 */

@Getter
@Setter
public class MappingRules {

    private String ruleId;
    private RuleMatch match;
    private JsonNode network;
}
