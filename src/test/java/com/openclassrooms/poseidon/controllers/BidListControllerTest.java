package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.BidList;
import com.openclassrooms.poseidon.services.BidListService;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// SpringRunner is an alias of SpringJUnit4ClassRunner
// it's a Spring extension of JUnit that handles the TestContext
// The SpringBootTest annotation can be used to run a Spring Boot application as a test,
// and provides the following features:
// - It loads the Spring application context for the test.
// - It can be used to test a slice of the application, including just the controllers, or the whole application.
// - It can be used to test a single layer (e.g., only the controllers) or the whole application (all layers).
// Spring will charge this bean MockMvc to provide a checker of all of your endpoints.
@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class BidListControllerTest {

    // we mock the service, here we test only the controller
    // @MockBean is a Spring annotation that depends on mockito framework
    @MockBean
    BidListService bidListService;

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
    public void getRequestBidListViewTest() throws Exception {
        // GIVEN
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        List<BidList> bidList = new ArrayList<>();
        bidList.add(bid);

        // we set the result of the mocked service
        doReturn(bidList)
                .when(bidListService)
                .getAllBids();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidList"))
                .andReturn();
        assertEquals("Account Test", bidList.get(0).getAccount());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestBidListAddViewTest() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidList"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postRequestBidListValidateTest() throws Exception {
        // GIVEN
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        List<BidList> bidList = new ArrayList<>();
        bidList.add(bid);

        // we set the result of the mocked service
        doNothing()
                .when(bidListService)
                .addBid(bid);
        doReturn(bidList)
                .when(bidListService)
                .getAllBids();
        // WHEN
        // the test is executed:
        // perform: it executes the request and returns a ResultActions object
        // andExpect: ResultMatcher object that defines some expectations
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                        .flashAttr("successSaveMessage", "Bid successfully added to list")
                        .param("bidListId", "1")
                        .param("account", "Account Test")
                        .param("type", "Type Test")
                        .param("bidQuantity", "10"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(flash().attributeExists("successSaveMessage"))
                .andReturn();
        assertEquals("Account Test", bidList.get(0).getAccount());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestBidListUpdateTest() throws Exception {
        // GIVEN
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        // we set the result of the mocked service
        doReturn(true)
                .when(bidListService)
                .checkIfIdExists(bid.getBidListId());

        doReturn(bid)
                .when(bidListService)
                .getBidByBidListId(bid.getBidListId());
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/{id}", "1"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidList"))
                .andReturn();
        assertEquals("Account Test", bid.getAccount());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postRequestBidListUpdateTest() throws Exception {
        // WHEN
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        List<BidList> bidList = new ArrayList<>();
        bidList.add(bid);

        doReturn(true)
                .when(bidListService)
                .checkIfIdExists(bid.getBidListId());
        doNothing()
                .when(bidListService)
                .addBid(bid);
        doReturn(bidList)
                .when(bidListService)
                .getAllBids();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/{id}", "1")
                        .flashAttr("successUpdateMessage", "Bid successfully updated")
                        .param("bidListId", "1")
                        .param("account", "Account Test")
                        .param("type", "Type Test")
                        .param("bidQuantity", "10"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(flash().attributeExists("successUpdateMessage"))
                .andReturn();
        assertEquals("Account Test", bidList.get(0).getAccount());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestBidListDeleteTest() throws Exception {
        // GIVEN
        BidList bidModel = new BidList();
        bidModel.setBidListId(1);
        bidModel.setAccount("Account Test");
        bidModel.setType("Type Test");
        bidModel.setBidQuantity(10d);

        List<BidList> bidList = new ArrayList<>();
        bidList.add(bidModel);

        
        doReturn(true)
                .when(bidListService)
                .checkIfIdExists(bidModel.getBidListId());
        doNothing()
                .when(bidListService)
                .deleteBidById(bidModel.getBidListId());
        doReturn(bidList)
                .when(bidListService)
                .getAllBids();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/{id}", "1")
                        .flashAttr("successDeleteMessage", "DELETE Bid : OK"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(flash().attributeExists("successDeleteMessage"))
                .andReturn();
        assertEquals("Account Test", bidList.get(0).getAccount());
    }
}
