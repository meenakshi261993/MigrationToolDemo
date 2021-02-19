package com.migration.demo.mappingRule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Holds the match object details of given rule
 *
 */
@Getter
@Setter
public class RuleMatch {

    private  String model;
    private List<String> paramExists;
    private String paramAbsent;

}
