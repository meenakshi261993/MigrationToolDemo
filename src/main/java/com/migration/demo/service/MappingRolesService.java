package com.migration.demo.service;

import com.migration.demo.mappingRule.MappingRules;
import com.migration.demo.parsers.RuleParser;
import com.migration.demo.repository.MappingRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MappingRolesService {

    @Autowired
    private MappingRolesRepository mappingRolesRepository;

    @Autowired
    private  RuleParser ruleParser;


    /**
     * Returns the particular rule based on the ruleId from repository
     *
     * @param ruleId
     * @return
     */
    public Optional<MappingRules> getMappingRule(String ruleId){
        return mappingRolesRepository.getMappingRule(ruleId);
    }

    /**
     * Save the rules into the repository
     *
     * @param mappingRules
     */
    public void createMappingRule(String mappingRules){
        List<MappingRules> rules = ruleParser.parseJson(mappingRules);
          mappingRolesRepository.saveAll(rules);
    }

    /**
     * Returns the map containing all configured rules in repository
     *
     * @return
     */
    public Map<String, MappingRules> getAllMappingRules(){

        return mappingRolesRepository.getRulesMap();
    }
}
