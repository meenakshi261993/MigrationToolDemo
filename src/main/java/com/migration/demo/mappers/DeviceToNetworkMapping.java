package com.migration.demo.mappers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.migration.demo.mappingRule.MappingRules;
import com.migration.demo.mappingRule.RuleMatch;
import com.migration.demo.service.MappingRolesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class DeviceToNetworkMapping {
    
    @Autowired
    MappingRolesService mappingRolesService;

    private static final Logger logger = LoggerFactory.getLogger(DeviceToNetworkMapping.class);

    /**
     * Returns the List of network service based on the input model
     *
     * @param inputModel input model map
     * @param model  String
     * @return  List<JsonNode>
     */
    public List<JsonNode> mapDeviceModelToNetwork(Map<String,String> inputModel, String model){
        List<MappingRules> mappingRulesList = getMappingRulesList();
        List<JsonNode> networkList = getMappedNetworkJsonNodes(inputModel, model, mappingRulesList);
        logger.debug("Mapped network services " +networkList.toString());
        return networkList;
    }

    /**
     *
     * @param inputModel input model map
     * @param model   string it can be JsonValue or keyValue
     * @param mappingRulesList  mapping rules list
     * @return List<JsonNode>
     */
    private List<JsonNode> getMappedNetworkJsonNodes(Map<String, String> inputModel, String model, List<MappingRules> mappingRulesList) {
        Iterator<MappingRules> ruleIterator = mappingRulesList.iterator();
        List<JsonNode> networkList = new ArrayList<>();
        while(ruleIterator.hasNext()) {
            MappingRules rule = ruleIterator.next();
            RuleMatch matchObject = rule.getMatch();
            String paramAbsent = matchObject.getParamAbsent();
            List<String> paramExists = matchObject.getParamExists();
            String ruleModelName = matchObject.getModel();
            if (ruleModelName.equals(model)) {
                if (paramAbsent != null && paramExists.size() > 0) {
                    parseRuleParamExistsAndParamAbsent(inputModel, networkList, rule, paramAbsent, paramExists);
                } else if (paramAbsent == null && paramExists.size() > 0) {
                    parseRuleParamExists(inputModel, networkList, rule, paramExists);
                }
            }
        }
        return networkList;
    }

    /**
     *Check paramExists list against the input data
     *
     * @param inputModel input model map
     * @param networkList list of network matched
     * @param rule    mapping rule object
     * @param paramExists list of paramExits from RuleMatch  object
     */
    private void parseRuleParamExists(Map<String, String> inputModel, List<JsonNode> networkList, MappingRules rule, List<String> paramExists) {
        JsonNode networkNode;
        if (checkParamExistsInInput(inputModel, paramExists)) {
            try {
                networkNode = generateNetworkService(inputModel, rule.getNetwork());
                networkList.add(networkNode);
            } catch (JsonProcessingException e) {
               logger.error("Error occurred during the network node updating based on input model" +e.getMessage());
            }
        }
    }

    /**
     * Check paramExists list and paramAbsent against the input data
     *
     * @param inputModel input model map
     * @param networkList list of network matched
     * @param rule mapping rule object
     * @param paramAbsent paramAbsent from RuleMatch  object
     * @param paramExists list of paramExits from RuleMatch  object
     */
    private void parseRuleParamExistsAndParamAbsent(Map<String, String> inputModel, List<JsonNode> networkList, MappingRules rule, String paramAbsent, List<String> paramExists) {
        if (!inputModel.containsKey(paramAbsent)) {
            parseRuleParamExists(inputModel, networkList, rule, paramExists);
        }
    }

    /**
     *Updates the network JSON node based on the input
     *
     * @param inputModel input model map
     * @param network mapped network JSON node
     * @return JsonNode updated network JSON node
     * @throws JsonProcessingException
     */
    private JsonNode generateNetworkService(Map<String, String> inputModel, JsonNode network) throws JsonProcessingException {
        Iterator<Map.Entry<String, JsonNode>> networkFields = network.fields();
        while(networkFields.hasNext()){
            Map.Entry<String, JsonNode> field = networkFields.next();
            if( field.getValue().isValueNode() &&inputModel.containsKey(field.getValue().textValue())){
                updateNetworkBasedOnInput(inputModel, (ObjectNode) network, field);
            }else if (field.getValue().isObject()){
                parseObjectNodeInNetworkJsonNode(inputModel, field);
            }
        }

        return network;
    }

    /**
     *Updates the model details based on the input
     *
     * @param inputModel input model map
     * @param field  map entry fields to be updated with input data
     */
    private void parseObjectNodeInNetworkJsonNode(Map<String, String> inputModel, Map.Entry<String, JsonNode> field) {
        JsonNode paramsNode = field.getValue();
        Iterator<Map.Entry<String, JsonNode>> paramsField = paramsNode.fields();
        while(paramsField.hasNext()){
            Map.Entry<String, JsonNode> params = paramsField.next();
            if( params.getValue().isValueNode() && inputModel.containsKey(params.getValue().textValue())){
                updateNetworkBasedOnInput(inputModel, (ObjectNode) paramsNode, params);
            }
        }
    }

    /**
     *Updates the fields based on input
     *
     * @param inputModel input model map
     * @param network  matched network objectNode
     * @param field field to be updated with input data
     */
    private void updateNetworkBasedOnInput(Map<String, String> inputModel, ObjectNode network, Map.Entry<String, JsonNode> field) {
        String modelName = inputModel.get(field.getValue().textValue());
        network.put(field.getKey(), modelName);
    }

    /**
     *Checks the input model against the paramExists
     *
     * @param inputModel  input model map
     * @param paramExists  list paramExists from match object of rule
     * @return boolean value
     */
    private boolean checkParamExistsInInput(Map<String, String> inputModel, List<String> paramExists) {
        if(inputModel.keySet().containsAll(paramExists)){
            return true;
        }
        return false;
    }

    /**
     *
     * @return Mapping rules configured
     */
    private List<MappingRules> getMappingRulesList() {
        Map<String, MappingRules> mappingRules = mappingRolesService.getAllMappingRules();
        List<MappingRules> mappingRulesList = new ArrayList<>();
        for(  Map.Entry<String, MappingRules> entry :mappingRules.entrySet()){
           MappingRules rules =  entry.getValue();
            mappingRulesList.add(rules);
        }
        return mappingRulesList;
    }

}
