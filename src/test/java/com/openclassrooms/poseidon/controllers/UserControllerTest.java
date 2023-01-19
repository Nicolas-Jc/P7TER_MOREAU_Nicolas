package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.User;
import com.openclassrooms.poseidon.services.UserService;
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
public class UserControllerTest {

    // we mock the service, here we test only the controller
    // @MockBean is a Spring annotation that depends on mockito framework
    @MockBean
    UserService userService;

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
    public void getRequestUserViewTest() throws Exception {
        // GIVEN
        User user = new User();
        user.setId(1);
        user.setFullname("FullName");
        user.setUsername("admin@ocr.fr");
        user.setPassword("Admin_1701");
        user.setRole("ADMIN");

        List<User> userList = new ArrayList<>();
        userList.add(user);

        doReturn(userList)
                .when(userService)
                .getAllUsers();
        // WHEN
        mockMvc.perform(get("/user/list"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))
                .andReturn();
        assertEquals("ADMIN", userList.get(0).getRole());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestUserAddViewTest() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("user"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postRequestUserValidateTest() throws Exception {
        // GIVEN
        User user = new User();
        user.setId(1);
        user.setFullname("FullName");
        user.setUsername("admin@ocr.fr");
        user.setPassword("Admin_1701");
        user.setRole("ADMIN");

        List<User> userList = new ArrayList<>();
        userList.add(user);

        // we set the result of the mocked service
        doNothing()
                .when(userService)
                .saveUser(user);
        doReturn(userList)
                .when(userService)
                .getAllUsers();
        // WHEN
        // the test is executed:
        // perform: it executes the request and returns a ResultActions object
        // andExpect: ResultMatcher object that defines some expectations
        mockMvc.perform(post("/user/validate")
                        .flashAttr("successSaveMessage", "User successfully added to list ")
                        .param("Id", "1")
                        .param("fullname", "FullName")
                        .param("username", "admin@ocr.fr")
                        .param("password", "Admin_1701")
                        .param("role", "ADMIN"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(flash().attributeExists("successSaveMessage"))
                .andReturn();
        assertEquals("admin@ocr.fr", userList.get(0).getUsername());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestUserUpdateTest() throws Exception {
        // GIVEN
        User user = new User();
        user.setId(1);
        user.setFullname("FullName");
        user.setUsername("admin@ocr.fr");
        user.setPassword("Admin_1701");
        user.setRole("ADMIN");

        // we set the result of the mocked service
        doReturn(true)
                .when(userService)
                .checkIfUserExistsById(user.getId());

        doReturn(user)
                .when(userService)
                .getUserById(user.getId());
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/{id}", "1"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"))
                .andReturn();
        assertEquals("admin@ocr.fr", user.getUsername());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void postRequestTradeUpdateTest() throws Exception {
        // GIVEN
        User user = new User();
        user.setId(1);
        user.setFullname("FullName");
        user.setUsername("admin@ocr.fr");
        user.setPassword("Admin_1701");
        user.setRole("ADMIN");

        List<User> userList = new ArrayList<>();
        userList.add(user);

        doReturn(true)
                .when(userService)
                .checkIfUserExistsById(user.getId());
        doNothing()
                .when(userService)
                .saveUser(user);
        doReturn(userList)
                .when(userService)
                .getAllUsers();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/{id}", "1")
                        .flashAttr("successUpdateMessage", "User successfully updated")
                        .param("Id", "1")
                        .param("fullname", "FullName")
                        .param("username", "admin@ocr.fr")
                        .param("password", "Admin_1701")
                        .param("role", "ADMIN"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(flash().attributeExists("successUpdateMessage"))
                .andReturn();
        assertEquals("FullName", userList.get(0).getFullname());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    public void getRequestUserDeleteTest() throws Exception {
        // GIVEN
        User user = new User();
        user.setId(1);
        user.setFullname("FullName");
        user.setUsername("admin@ocr.fr");
        user.setPassword("Admin_1701");
        user.setRole("ADMIN");

        List<User> userList = new ArrayList<>();
        userList.add(user);

        doReturn(true)
                .when(userService)
                .checkIfUserExistsById(user.getId());
        doNothing()
                .when(userService)
                .deleteUserById(user.getId());
        doReturn(userList)
                .when(userService)
                .getAllUsers();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/{id}", "1")
                        .flashAttr("successDeleteMessage", "User successfully deleted"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(flash().attributeExists("successDeleteMessage"))
                .andReturn();
        assertEquals("FullName", userList.get(0).getFullname());
    }
}
