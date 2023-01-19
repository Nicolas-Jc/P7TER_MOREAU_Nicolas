package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.Rating;
import com.openclassrooms.poseidon.services.CustomUserDetailsService;
import com.openclassrooms.poseidon.services.RatingService;
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

@WebMvcTest(controllers = RatingController.class)
public class RatingControllerTest {

    // we mock the service, here we test only the controller
    // @MockBean is a Spring annotation that depends on mockito framework
    @MockBean
    RatingService ratingService;

    @MockBean
    @SuppressWarnings("unused")
    private CustomUserDetailsService userDetailsService;

    // we inject the server side Spring MVC test support
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser
    public void getRequestRatingViewTest() throws Exception {
        // GIVEN
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(100);

        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating);

        doReturn(ratingList)
                .when(ratingService)
                .getAllRatings();
        // WHEN
        mockMvc.perform(get("/rating/list"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("rating"))
                .andReturn();
        Assertions.assertEquals(100, (int) ratingList.get(0).getOrderNumber());
    }

    @Test
    @WithMockUser
    public void getRequestRatingAddViewTest() throws Exception {
        // GIVEN
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("rating"))
                .andReturn();
    }

    @Test
    @WithMockUser
    public void postRequestRatingValidateTest() throws Exception {
        // GIVEN
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(100);

        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating);

        // we set the result of the mocked service
        doNothing()
                .when(ratingService)
                .saveRating(rating);
        doReturn(ratingList)
                .when(ratingService)
                .getAllRatings();
        // WHEN
        // the test is executed:
        // perform: it executes the request and returns a ResultActions object
        // andExpect: ResultMatcher object that defines some expectations
        mockMvc.perform(post("/rating/validate")
                        .flashAttr("successSaveMessage", "Rating successfully added to list")
                        .param("id", "1")
                        .param("moodysRating", "Moodys Rating")
                        .param("sandPRating", "Sand PRating")
                        .param("fitchRating", "Fitch Rating")
                        .param("orderNumber", "100")
                        .with(csrf()))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(flash().attributeExists("successSaveMessage"))
                .andReturn();
        Assertions.assertEquals(100, (int) ratingList.get(0).getOrderNumber());
    }

    @Test
    @WithMockUser
    public void getRequestRatingUpdateTest() throws Exception {
        // GIVEN
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(100);

        // we set the result of the mocked service
        doReturn(true)
                .when(ratingService)
                .checkIfIdExists(rating.getId());

        doReturn(rating)
                .when(ratingService)
                .getRatingById(rating.getId());
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/{id}", "1"))
                // THEN
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("rating"))
                .andReturn();
        Assertions.assertEquals(100, (int) rating.getOrderNumber());

    }

    @Test
    @WithMockUser
    public void postRequestRatingUpdateTest() throws Exception {
        // GIVEN
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(100);

        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating);

        doReturn(true)
                .when(ratingService)
                .checkIfIdExists(rating.getId());
        doNothing()
                .when(ratingService)
                .saveRating(rating);
        doReturn(ratingList)
                .when(ratingService)
                .getAllRatings();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/{id}", "1")
                        .flashAttr("successUpdateMessage", "Rating successfully updated")
                        .param("id", "1")
                        .param("moodysRating", "Moodys Rating")
                        .param("sandPRating", "Sand PRating")
                        .param("fitchRating", "Fitch Rating")
                        .param("orderNumber", "100")
                        .with(csrf()))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(flash().attributeExists("successUpdateMessage"))
                .andReturn();
        Assertions.assertEquals(100, (int) ratingList.get(0).getOrderNumber());
    }

    @Test
    @WithMockUser
    public void getRequestRatingDeleteTest() throws Exception {
        // GIVEN
        Rating rating = new Rating();
        rating.setId(1);
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(100);

        List<Rating> ratingList = new ArrayList<>();
        ratingList.add(rating);

        doReturn(true)
                .when(ratingService)
                .checkIfIdExists(rating.getId());
        doNothing()
                .when(ratingService)
                .deleteRatingById(rating.getId());
        doReturn(ratingList)
                .when(ratingService)
                .getAllRatings();
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/{id}", "1")
                        .flashAttr("successDeleteMessage", "CurvePoint successfully deleted"))
                // THEN
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(flash().attributeExists("successDeleteMessage"))
                .andReturn();
        Assertions.assertEquals(1, (int) ratingList.get(0).getId());
    }
}
