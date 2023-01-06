package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.models.Rating;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private static final Logger logger = LogManager.getLogger(RatingService.class);

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRep) {
        this.ratingRepository = ratingRep;
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public boolean checkIfIdExists(int id) {
        return ratingRepository.existsById(id);
    }

    public void saveRating(Rating rating) {
        ratingRepository.save(rating);
        logger.info("Rating Id:{} was saved to Rating List", rating.getId());
    }

    public void deleteRatingById(int id) {
        ratingRepository.deleteById(id);
        logger.info("Delete Rating : OK");
    }

    public Rating getRatingById(int id) {
        return ratingRepository.findById(id);
    }
}
