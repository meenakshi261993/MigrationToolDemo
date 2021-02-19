# MigrationToolDemo
Demonstration of the network Migration

The Migration tool contains below major steps once the application in running to serve the migration functionality.

1.Rest API exposed to configure the mapping rules for the network migration,So we need to configure the mapping rules via exposed REST API.
Note:Here I defined the mapping rules in JSON format attached the template in resources/RuleTemplate/MappingRule.json

2.Reading the input Model files placed in the input directory
Note:Currently we are using the only two models json and txt file models as stated in problem.

3.For the each input model, get all configured rules and perform the rule match for given input model to convert into new network service model.

4.Write the network service nodes into the yaml output file in the defined filename synatx in output directory.

This Approach is in such a way that if new models are getting added in the network only below mentioned changes are needed

1. Define the mapping rules for the new model and configure  via defined REST API.
2. We just need to write the parser to parse the input model.


# Endpoint design specification:

MigrationTool Swagger UI : http://localhost:8094/swagger-ui-custom.html

API Spec in json : http://localhost:8094/api-spec

API Spec in yaml : http://localhost:8094/api-spec.yaml (will download the spec file).

# Build and run :
cd MigrationToolDemo/
mvn clean package
mvn spring-boot:run
Or
cd /target
java -jar migrationTool-1.0.0-SNAPSHOT.war

# Testing Action :
App runs at pre-configured port : 8094

End-points : There are 2 endpoints exposed in this tool:

POST

/api/migration/rules
sample response:
201 created 
This will create  mapping rules.

GET

/api/migration/rules/{ruleId}
sample response:
{
    "ruleId": "6",
    "match": {
        "model": "JsonValue",
        "paramExists": [
            "param2"
        ],
        "paramAbsent": "param1"
    },
    "network": {
        "network_service_type": "B",
        "device_id": "deviceId",
        "configuration": {
            "configB1": "",
            "configB2": "param2"
        }
    }
}

Create mapping rules.
curl --header "Content-Type: application/json" --request POST http://localhost:8094/api/migration/rules -d  @rules.json

Dispaly configured rule 
curl --header "Content-Type: application/json" --request GET http://localhost:8094/api/migration/rules/{ruleId}


# place the input model files in input directory and wait for the output yaml file
