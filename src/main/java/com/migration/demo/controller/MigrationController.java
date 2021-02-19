package com.migration.demo.controller;

import com.migration.demo.mappingRule.MappingRules;
import com.migration.demo.service.MappingRolesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Migration Tool", description = "The Migration tool API")
public class MigrationController {

    @Autowired
    private MappingRolesService mappingRolesService;

    @Operation(summary = "Creates the mapping rules for migration process")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mapping rules created",content =@Content),
            @ApiResponse(responseCode = "404", description = "Mapping rules not created.",content = @Content)})

    @PostMapping(value = "/migration/rules" ,consumes = "application/json", produces = "application/json")
    public ResponseEntity createMappingRules(@RequestBody String mappingRuleJson) {
        mappingRolesService.createMappingRule(mappingRuleJson);
        return new ResponseEntity(new HttpHeaders(), HttpStatus.CREATED);
    }

    @Operation(summary = "Display the particular Mapping rule based on ruleId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mapping rule fetched successful",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Mapping rule not found.",
                    content = @Content)})
    @GetMapping("/migration/rules/{id}")
    public ResponseEntity<MappingRules> getMappingRules(@PathVariable String id){
        return new ResponseEntity(mappingRolesService.getMappingRule(id), new HttpHeaders(), HttpStatus.OK);
    }


}
