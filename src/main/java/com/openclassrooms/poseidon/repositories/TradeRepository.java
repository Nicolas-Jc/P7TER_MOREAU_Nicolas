package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.models.TradeModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TradeRepository extends JpaRepository<TradeModel, Integer> {

    TradeModel findById(int id);

}
