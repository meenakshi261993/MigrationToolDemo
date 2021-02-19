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
