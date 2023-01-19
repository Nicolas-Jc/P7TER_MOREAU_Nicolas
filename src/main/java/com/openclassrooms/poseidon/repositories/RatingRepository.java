package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Rating findById(int id);
}
