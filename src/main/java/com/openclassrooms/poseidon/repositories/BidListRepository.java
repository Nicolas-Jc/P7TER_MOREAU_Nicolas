package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.models.BidList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BidListRepository extends JpaRepository<BidList, Integer> {

    BidList findByBidListId(int bidListId);

}
