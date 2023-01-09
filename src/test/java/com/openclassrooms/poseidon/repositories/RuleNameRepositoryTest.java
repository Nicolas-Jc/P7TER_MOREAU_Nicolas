package com.openclassrooms.poseidon.repositories;


import com.openclassrooms.poseidon.models.Rating;
import com.openclassrooms.poseidon.models.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class RuleNameRepositoryTest {
    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    @DisplayName("RuleNameRepository ==>  RuleName SAVE, UPDATE, FIND, DELETE - UNIT TESTS")
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
        rule = ruleNameRepository.save(rule);
        // THEN
        Assertions.assertNotNull(rule.getId());
        Assertions.assertEquals("Rule Name", rule.getName());

        // *************** UPDATE ***********************************
        // GIVEN
        rule.setName("Rule Name Updated");
        rule.setSqlStr("SqlStr Updated");

        // WHEN
        rule = ruleNameRepository.save(rule);
        // THEN
        Assertions.assertEquals("Rule Name Updated", rule.getName());
        Assertions.assertEquals("SqlStr Updated", rule.getSqlStr());

        // *************** CHECK IF EXISTS BY ID ***********************************
        // GIVEN
        Integer id = rule.getId();
        // WHEN
        boolean checkIfIdExists = ruleNameRepository.existsById(id);
        // THEN
        Assertions.assertTrue(checkIfIdExists);

        // *************** FIND ALL ***********************************
        // WHEN
        List<Rule> listResult = ruleNameRepository.findAll();
        // THEN
        Assertions.assertTrue(listResult.size() > 0);

        // *************** DELETE ***********************************
        // GIVEN
        //Integer id3 = curvePoint.getBidListId();
        // WHEN
        ruleNameRepository.deleteById(id);
        // THEN
        Optional<Rule> ruleDel = ruleNameRepository.findById(id);
        Assertions.assertFalse(ruleDel.isPresent());

    }

}
