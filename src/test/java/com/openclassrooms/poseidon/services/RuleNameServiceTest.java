package com.openclassrooms.poseidon.services;


import com.openclassrooms.poseidon.models.Rule;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class RuleNameServiceTest {
    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Autowired
    private RuleNameService ruleNameService;

    @Test
    @DisplayName("RuleNameService ==>  RuleName SAVE, UPDATE, FIND, DELETE - UNIT TESTS")
    void ruleNameTest() {
        // *************** SAVE ***********************************
        // GIVEN
        Rule rule = new Rule();
        rule.setName("Rule Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SqlStr");
        rule.setSqlPart("SqlPart");
        // WHEN
        ruleNameService.saveRuleName(rule);
        // THEN
        Assertions.assertNotNull(rule.getId());
        Assertions.assertEquals("Sand PRating", rule.getName());

        // *************** UPDATE ***********************************
        // GIVEN
        rule.setName("Rule Name Updated");
        rule.setSqlStr("SqlStr Updated");
        // WHEN
        ruleNameService.saveRuleName(rule);
        // THEN
        Assertions.assertEquals("Rule Name Updated", rule.getName());
        Assertions.assertEquals("SqlStr Updated", rule.getSqlStr());

        // *************** CHECK IF EXISTS BY ID ***********************************
        // GIVEN
        Integer id = rule.getId();
        // WHEN
        boolean checkIfIdExists = ruleNameService.checkIfIdExists(id);
        // THEN
        Assertions.assertTrue(checkIfIdExists);

        // *************** FIND ALL ***********************************
        // WHEN
        List<Rule> listResult = ruleNameService.getAllRuleNames();
        // THEN
        Assertions.assertTrue(listResult.size() > 0);

        // *************** DELETE ***********************************
        // GIVEN
        // WHEN
        ruleNameService.deleteRuleNameById(id);
        // THEN
        Optional<Rule> ruleDel = Optional.ofNullable(ruleNameService.getRuleNameById(id));
        Assertions.assertFalse(ruleDel.isPresent());

    }

}
