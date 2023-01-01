package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.models.BidListModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BidListRepository extends JpaRepository<BidListModel, Integer> {

    BidListModel findByBidListId(int bidListId);

}
