package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.Trade;
import com.openclassrooms.poseidon.services.CustomUserDetailsService;
import com.openclassrooms.poseidon.services.TradeService;
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

@WebMvcTest(controllers = TradeController.class)
public class TradeControllerTest {

    // we mock the service, here we test only the controller
    // @MockBean is a Spring annotation that depends on mockito framework
    @MockBean
    TradeService tradeService;

    @MockBean
    @SuppressWarnings("unused")
    private CustomUserDetailsService userDetailsService;

    // we inject the server side Spring MVC test support
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void getRequestTradeViewTest() throws Exception {
        // GIVEN
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setBuyQuantity(100d);

        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(trade);

        doReturn(tradeList)
                .when(tradeService)
                .getAllTrades();
        // WHEN
        mockMvc.perform(get("/trade/list"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trade"))
                .andReturn();
        Assertions.assertEquals("Account", tradeList.get(0).getAccount());
    }

    @Test
    @WithMockUser
    public void getRequestTradeAddViewTest() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("trade"))
                .andReturn();
    }

    @Test
    @WithMockUser
    public void postRequestTradeValidateTest() throws Exception {
        // GIVEN
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setBuyQuantity(100d);

        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(trade);

        // we set the result of the mocked service
        doNothing()
                .when(tradeService)
                .addTrade(trade);
        doReturn(tradeList)
                .when(tradeService)
                .getAllTrades();
        // WHEN
        // the test is executed:
        // perform: it executes the request and returns a ResultActions object
        // andExpect: ResultMatcher object that defines some expectations
        mockMvc.perform(post("/trade/validate")
                        .flashAttr("successSaveMessage", "Trade successfully added to list")
                        .param("tradeId", "1")
                        .param("account", "Account")
                        .param("type", "Type")
                        .param("buyQuantity", "100")
                        .with(csrf()))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(flash().attributeExists("successSaveMessage"))
                .andReturn();
        Assertions.assertEquals("Account", tradeList.get(0).getAccount());
    }

    @Test
    @WithMockUser
    public void getRequestTradeUpdateTest() throws Exception {
        // GIVEN
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setBuyQuantity(100d);

        // we set the result of the mocked service
        doReturn(true)
                .when(tradeService)
                .checkIfTradeIdExists(trade.getTradeId());

        doReturn(trade)
                .when(tradeService)
                .getTradeById(trade.getTradeId());
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/{id}", "1"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("trade"))
                .andReturn();
        Assertions.assertEquals("Account", trade.getAccount());

    }

    @Test
    @WithMockUser
    public void postRequestTradeUpdateTest() throws Exception {
        // GIVEN
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setBuyQuantity(100d);

        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(trade);

        doReturn(true)
                .when(tradeService)
                .checkIfTradeIdExists(trade.getTradeId());
        doNothing()
                .when(tradeService)
                .addTrade(trade);
        doReturn(tradeList)
                .when(tradeService)
                .getAllTrades();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/{id}", "1")
                        .flashAttr("successUpdateMessage", "Trade successfully updated")
                        .param("tradeId", "1")
                        .param("account", "Account")
                        .param("Type", "Type")
                        .param("buyQuantity", "100")
                        .with(csrf()))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(flash().attributeExists("successUpdateMessage"))
                .andReturn();
        Assertions.assertEquals("Type", tradeList.get(0).getType());
    }

    @Test
    @WithMockUser
    public void getRequestUserDeleteTest() throws Exception {
        // GIVEN
        Trade trade = new Trade();
        trade.setTradeId(1);
        trade.setAccount("Account");
        trade.setType("Type");
        trade.setBuyQuantity(100d);

        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(trade);

        doReturn(true)
                .when(tradeService)
                .checkIfTradeIdExists(trade.getTradeId());
        doNothing()
                .when(tradeService)
                .deleteTradeById(trade.getTradeId());
        doReturn(tradeList)
                .when(tradeService)
                .getAllTrades();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/{id}", "1")
                        .flashAttr("successDeleteMessage", "Trade successfully deleted"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(flash().attributeExists("successDeleteMessage"))
                .andReturn();
        Assertions.assertEquals("Type", tradeList.get(0).getType());
    }

}
