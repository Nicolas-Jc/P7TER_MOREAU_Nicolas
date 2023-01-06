package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.models.Trade;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TradeRepository extends JpaRepository<Trade, Integer> {

    Trade findById(int id);

}
