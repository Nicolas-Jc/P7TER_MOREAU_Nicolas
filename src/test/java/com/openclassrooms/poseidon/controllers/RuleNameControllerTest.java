package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.Rating;
import com.openclassrooms.poseidon.models.Rule;
import com.openclassrooms.poseidon.services.RatingService;
import com.openclassrooms.poseidon.services.RuleNameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class RuleNameControllerTest {

    // we mock the service, here we test only the controller
    // @MockBean is a Spring annotation that depends on mockito framework
    @MockBean
    RuleNameService ruleNameService;

    @Autowired
    private WebApplicationContext webContext;

    // we inject the server side Spring MVC test support
    private MockMvc mockMvc;

    @Before
    public void setupMockmvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
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
        assertEquals("SqlPart", ruleList.get(0).getSqlPart());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
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
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
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
                        .param("sqlPart", "SqlPart"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(flash().attributeExists("successSaveMessage"))
                .andReturn();
        assertEquals("SqlPart", ruleList.get(0).getSqlPart());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
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
        assertEquals("SqlPart", rule.getSqlPart());

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
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
                        .param("sqlPart", "SqlPart"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(flash().attributeExists("successUpdateMessage"))
                .andReturn();
        assertEquals("SqlPart", ruleList.get(0).getSqlPart());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
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
        assertEquals(1, (int) ruleList.get(0).getId());
    }

}
