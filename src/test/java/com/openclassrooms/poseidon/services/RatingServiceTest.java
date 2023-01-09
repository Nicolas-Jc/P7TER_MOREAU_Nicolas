package com.openclassrooms.poseidon.services;


import com.openclassrooms.poseidon.models.Rating;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class RatingServiceTest {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RatingService ratingService;

    @Test
    @DisplayName("RatingService ==>  Rating SAVE, UPDATE, FIND, DELETE - UNIT TESTS")
    void ratingTest() {
        // *************** SAVE ***********************************
        // GIVEN
        Rating rating = new Rating();
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(11);
        // WHEN
        ratingService.saveRating(rating);
        // THEN
        Assertions.assertNotNull(rating.getId());
        Assertions.assertEquals("Moodys Rating", rating.getMoodysRating());
        Assertions.assertEquals("Sand PRating", rating.getSandPRating());
        Assertions.assertEquals("Fitch Rating", rating.getFitchRating());
        Assertions.assertEquals(11, rating.getOrderNumber());

        // *************** UPDATE ***********************************
        // GIVEN
        rating.setMoodysRating("Moodys Rating Updated");
        rating.setSandPRating("Sand PRating Updated");
        rating.setFitchRating("Fitch Rating Updated");
        rating.setOrderNumber(50);
        // WHEN
        ratingService.saveRating(rating);
        // THEN
        Assertions.assertEquals("Moodys Rating Updated", rating.getMoodysRating());
        Assertions.assertEquals("Sand PRating Updated", rating.getSandPRating());
        Assertions.assertEquals("Fitch Rating Updated", rating.getFitchRating());
        Assertions.assertEquals(11, rating.getOrderNumber());

        // *************** CHECK IF EXISTS BY ID ***********************************
        // GIVEN
        Integer id = rating.getId();
        // WHEN
        boolean checkIfIdExists = ratingService.checkIfIdExists(id);
        // THEN
        Assertions.assertTrue(checkIfIdExists);

        // *************** FIND ALL ***********************************
        // WHEN
        List<Rating> listResult = ratingService.getAllRatings();
        // THEN
        Assertions.assertTrue(listResult.size() > 0);

        // *************** DELETE ***********************************
        // GIVEN
        // WHEN
        ratingService.deleteRatingById(id);
        // THEN
        Optional<Rating> ratingDel = Optional.ofNullable(ratingService.getRatingById(id));
        Assertions.assertFalse(ratingDel.isPresent());

    }

}
