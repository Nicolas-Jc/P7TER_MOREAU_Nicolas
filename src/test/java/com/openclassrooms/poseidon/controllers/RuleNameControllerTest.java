package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.Rule;
import com.openclassrooms.poseidon.services.CustomUserDetailsService;
import com.openclassrooms.poseidon.services.RuleNameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RuleNameController.class)
public class RuleNameControllerTest {

    // we mock the service, here we test only the controller
    // @MockBean is a Spring annotation that depends on mockito framework
    @MockBean
    RuleNameService ruleNameService;

    @MockBean
    @SuppressWarnings("unused")
    private CustomUserDetailsService userDetailsService;

    // we inject the server side Spring MVC test support
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void getRequestRuleNameViewTest() throws Exception {
        // GIVEN
        Rule rule = new Rule();
        rule.setId(1);
        rule.setName("Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SqlStr");
        rule.setSqlPart("SqlPart");

        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(rule);

        doReturn(ruleList)
                .when(ruleNameService)
                .getAllRuleNames();
        // WHEN
        mockMvc.perform(get("/ruleName/list"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("ruleName"))
                .andReturn();
        Assertions.assertEquals("SqlPart", ruleList.get(0).getSqlPart());
    }

    @Test
    @WithMockUser
    public void getRequestRuleNameAddViewTest() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleName"))
                .andReturn();
    }

    @Test
    @WithMockUser
    public void postRequestRuleNameValidateTest() throws Exception {
        // GIVEN
        Rule rule = new Rule();
        rule.setId(1);
        rule.setName("Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SqlStr");
        rule.setSqlPart("SqlPart");

        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(rule);

        // we set the result of the mocked service
        doNothing()
                .when(ruleNameService)
                .saveRuleName(rule);
        doReturn(ruleList)
                .when(ruleNameService)
                .getAllRuleNames();
        // WHEN
        // the test is executed:
        // perform: it executes the request and returns a ResultActions object
        // andExpect: ResultMatcher object that defines some expectations
        mockMvc.perform(post("/ruleName/validate")
                        .flashAttr("successSaveMessage", "Rule successfully added to list")
                        .param("id", "1")
                        .param("name", "Name")
                        .param("description", "Description")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SqlStr")
                        .param("sqlPart", "SqlPart")
                        .with(csrf()))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(flash().attributeExists("successSaveMessage"))
                .andReturn();
        Assertions.assertEquals("SqlPart", ruleList.get(0).getSqlPart());
    }

    @Test
    @WithMockUser
    public void getRequestRuleNameUpdateTest() throws Exception {
        // GIVEN
        Rule rule = new Rule();
        rule.setId(1);
        rule.setName("Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SqlStr");
        rule.setSqlPart("SqlPart");

        // we set the result of the mocked service
        doReturn(true)
                .when(ruleNameService)
                .checkIfIdExists(rule.getId());

        doReturn(rule)
                .when(ruleNameService)
                .getRuleNameById(rule.getId());
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/{id}", "1"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleName"))
                .andReturn();
        Assertions.assertEquals("SqlPart", rule.getSqlPart());

    }

    @Test
    @WithMockUser
    public void postRequestRuleNameUpdateTest() throws Exception {
        // GIVEN
        Rule rule = new Rule();
        rule.setId(1);
        rule.setName("Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SqlStr");
        rule.setSqlPart("SqlPart");

        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(rule);

        doReturn(true)
                .when(ruleNameService)
                .checkIfIdExists(rule.getId());
        doNothing()
                .when(ruleNameService)
                .saveRuleName(rule);
        doReturn(ruleList)
                .when(ruleNameService)
                .getAllRuleNames();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/{id}", "1")
                        .flashAttr("successUpdateMessage", "Rule successfully updated")
                        .param("id", "1")
                        .param("name", "Name")
                        .param("description", "Description")
                        .param("json", "Json")
                        .param("template", "Template")
                        .param("sqlStr", "SqlStr")
                        .param("sqlPart", "SqlPart")
                        .with(csrf()))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(flash().attributeExists("successUpdateMessage"))
                .andReturn();
        Assertions.assertEquals("SqlPart", ruleList.get(0).getSqlPart());
    }

    @Test
    @WithMockUser
    public void getRequestRuleNameDeleteTest() throws Exception {
        // GIVEN
        Rule rule = new Rule();
        rule.setId(1);
        rule.setName("Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SqlStr");
        rule.setSqlPart("SqlPart");

        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(rule);

        doReturn(true)
                .when(ruleNameService)
                .checkIfIdExists(rule.getId());
        doNothing()
                .when(ruleNameService)
                .deleteRuleNameById(rule.getId());
        doReturn(ruleList)
                .when(ruleNameService)
                .getAllRuleNames();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/{id}", "1")
                        .flashAttr("successDeleteMessage", "Rule successfully deleted"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(flash().attributeExists("successDeleteMessage"))
                .andReturn();
        Assertions.assertEquals(1, (int) ruleList.get(0).getId());
    }

}
