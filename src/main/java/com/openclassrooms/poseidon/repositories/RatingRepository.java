package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.models.RatingModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RatingRepository extends JpaRepository<RatingModel, Integer> {

    RatingModel findById(int id);


}
