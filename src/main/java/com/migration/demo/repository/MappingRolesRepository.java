package com.migration.demo.repository;

import com.migration.demo.mappingRule.MappingRules;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class MappingRolesRepository{

   private final  HashMap<String,MappingRules>  rulesMap = new HashMap<>();

    /**
     * Returns the particular rule based on the ruleId
     *
     * @param ruleId
     * @return
     */
    public Optional<MappingRules> getMappingRule(String ruleId){

        return Optional.ofNullable(rulesMap.get(ruleId));
    }

    /**
     * Save the list of rules into the map
     * @param rules
     */
    public void saveAll(List<MappingRules> rules) {
        for(MappingRules rule:rules){
          rulesMap.put(rule.getRuleId(),rule);
        }
    }

    /**
     * Returns the map containing all configured rules
     *
     * @return
     */
    public HashMap<String, MappingRules> getRulesMap() {
        return rulesMap;
    }
}
