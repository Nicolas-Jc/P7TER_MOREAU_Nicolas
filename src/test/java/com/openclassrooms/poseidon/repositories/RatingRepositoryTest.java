package com.openclassrooms.poseidon.repositories;


import com.openclassrooms.poseidon.models.CurvePoint;
import com.openclassrooms.poseidon.models.Rating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class RatingRepositoryTest {
    @Autowired
    private RatingRepository ratingRepository;

    @Test
    @DisplayName("RatingRepository ==>  Rating SAVE, UPDATE, FIND, DELETE - UNIT TESTS")
    void ratingTest() {
        // *************** SAVE ***********************************
        // GIVEN
        Rating rating = new Rating();
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(11);

        /*String stringDate = "2007-11-11 12:13:14";
        Timestamp timeStamp = Timestamp.valueOf(stringDate);
        rating.setAsOfDate(timeStamp);
        rating.setCreationDate(timeStamp);*/

        // WHEN
        rating = ratingRepository.save(rating);
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
        rating = ratingRepository.save(rating);
        // THEN
        Assertions.assertEquals("Moodys Rating Updated", rating.getMoodysRating());
        Assertions.assertEquals("Sand PRating Updated", rating.getSandPRating());
        Assertions.assertEquals("Fitch Rating Updated", rating.getFitchRating());
        Assertions.assertEquals(11, rating.getOrderNumber());

        // *************** CHECK IF EXISTS BY ID ***********************************
        // GIVEN
        Integer id = rating.getId();
        // WHEN
        boolean checkIfIdExists = ratingRepository.existsById(id);
        // THEN
        Assertions.assertTrue(checkIfIdExists);

        // *************** FIND ALL ***********************************
        // WHEN
        List<Rating> listResult = ratingRepository.findAll();
        // THEN
        Assertions.assertTrue(listResult.size() > 0);

        // *************** DELETE ***********************************
        // GIVEN
        //Integer id3 = curvePoint.getBidListId();
        // WHEN
        ratingRepository.deleteById(id);
        // THEN
        Optional<Rating> ratingDel = ratingRepository.findById(id);
        Assertions.assertFalse(ratingDel.isPresent());

    }

}
