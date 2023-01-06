package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.models.BidList;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BidListService {

    private static final Logger logger = LogManager.getLogger(BidListService.class);

    @Autowired
    private BidListRepository bidListRepository;

    @Autowired
    public BidListService(BidListRepository bidListRep) {
        this.bidListRepository = bidListRep;
    }

    public List<BidList> getAllBids() {
        return bidListRepository.findAll();
    }

    public boolean checkIfIdExists(int id) {
        return bidListRepository.existsById(id);
    }

    public void addBid(BidList bidList) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        bidList.setBidListDate(timestamp);
        bidList.setCreationDate(timestamp);
        bidListRepository.save(bidList);
        logger.info("Bid Id: {} was added to BidList", bidList.getBidListId());
    }

    public BidList getBidByBidListId(int bidListId) {
        return bidListRepository.findByBidListId(bidListId);
    }

    public void updateBid(BidList bidList) {
        bidListRepository.save(bidList);
        logger.info("UPDATE Bid : OK");
    }

    public void deleteBidById(int bidListId) {
        bidListRepository.deleteById(bidListId);
        logger.info("Delete Bid : OK");
    }
}
