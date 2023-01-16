package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.BidList;
import com.openclassrooms.poseidon.models.CurvePoint;
import com.openclassrooms.poseidon.services.BidListService;
import com.openclassrooms.poseidon.services.CurvePointService;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class CurveControllerTest {

    // we mock the service, here we test only the controller
    // @MockBean is a Spring annotation that depends on mockito framework
    @MockBean
    CurvePointService curvePointService;

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
    public void getRequestCurvePointViewTest() throws Exception {
        // GIVEN
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(30d);
        curvePoint.setValue(40d);
        curvePoint.setAsOfDate(timestamp);
        curvePoint.setCreationDate(timestamp);

        List<CurvePoint> curvePointList = new ArrayList<>();
        curvePointList.add(curvePoint);

        doReturn(curvePointList)
                .when(curvePointService)
                .getAllCurvePoints();
        // WHEN
        mockMvc.perform(get("/curvePoint/list"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoint"))
                .andReturn();
        assertEquals(10, (int) curvePointList.get(0).getCurveId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestCurvePointAddViewTest() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePoint"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postRequestCurvePointValidateTest() throws Exception {
        // GIVEN
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(30d);
        curvePoint.setValue(40d);
        curvePoint.setAsOfDate(timestamp);
        curvePoint.setCreationDate(timestamp);

        List<CurvePoint> curvePointList = new ArrayList<>();
        curvePointList.add(curvePoint);

        // we set the result of the mocked service
        doNothing()
                .when(curvePointService)
                .addCurvePoint(curvePoint);
        doReturn(curvePointList)
                .when(curvePointService)
                .getAllCurvePoints();
        // WHEN
        // the test is executed:
        // perform: it executes the request and returns a ResultActions object
        // andExpect: ResultMatcher object that defines some expectations
        mockMvc.perform(post("/curvePoint/validate")
                        .flashAttr("successSaveMessage", "CurvePoint successfully added to list")
                        .param("id", "1")
                        .param("curveId", "10")
                        .param("term", "30D")
                        .param("value", "40D"))
                //3. Assert
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(flash().attributeExists("successSaveMessage"))
                .andReturn();
        assertEquals(10, (int) curvePointList.get(0).getCurveId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestCurvePointUpdateTest() throws Exception {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(30d);
        curvePoint.setValue(40d);
        curvePoint.setAsOfDate(timestamp);
        curvePoint.setCreationDate(timestamp);

        // we set the result of the mocked service
        doReturn(true)
                .when(curvePointService)
                .checkIfIdExists(curvePoint.getId());

        doReturn(curvePoint)
                .when(curvePointService)
                .getCurvePointById(curvePoint.getId());
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/{id}", "1"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"))
                .andReturn();
        assertEquals(10, (int) curvePoint.getCurveId());

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postRequestCurvePointUpdateTest() throws Exception {
        // GIVEN
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(30d);
        curvePoint.setValue(40d);
        curvePoint.setAsOfDate(timestamp);
        curvePoint.setCreationDate(timestamp);

        List<CurvePoint> curvePointList = new ArrayList<>();
        curvePointList.add(curvePoint);

        doReturn(true)
                .when(curvePointService)
                .checkIfIdExists(curvePoint.getId());
        doNothing()
                .when(curvePointService)
                .addCurvePoint(curvePoint);
        doReturn(curvePointList)
                .when(curvePointService)
                .getAllCurvePoints();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/{id}", "1")
                        .flashAttr("successUpdateMessage", "CurvePoint successfully updated")
                        .param("id", "1")
                        .param("curveId", "10")
                        .param("term", "30D")
                        .param("value", "40D"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(flash().attributeExists("successUpdateMessage"))
                .andReturn();
        assertEquals(10, (int) curvePointList.get(0).getCurveId());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestCurvePointDeleteTest() throws Exception {
        // GIVEN
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(30d);
        curvePoint.setValue(40d);
        curvePoint.setAsOfDate(timestamp);
        curvePoint.setCreationDate(timestamp);

        List<CurvePoint> curvePointList = new ArrayList<>();
        curvePointList.add(curvePoint);


        doReturn(true)
                .when(curvePointService)
                .checkIfIdExists(curvePoint.getId());
        doNothing()
                .when(curvePointService)
                .deleteCurvePointById(curvePoint.getId());
        doReturn(curvePointList)
                .when(curvePointService)
                .getAllCurvePoints();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/{id}", "1")
                        .flashAttr("successDeleteMessage", "CurvePoint successfully deleted"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(flash().attributeExists("successDeleteMessage"))
                .andReturn();
        assertEquals(10, (int) curvePointList.get(0).getCurveId());
    }
}

