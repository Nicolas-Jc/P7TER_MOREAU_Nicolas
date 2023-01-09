package com.openclassrooms.poseidon.services;


import com.openclassrooms.poseidon.models.BidList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class BidListServiceTest {

    @Autowired
    private BidListService bidListService;

    @Test
    @DisplayName("BidListService ==>  BidList SAVE, UPDATE, FIND, DELETE - UNIT TESTS")
    void bidListTest() {
        // *************** SAVE ***********************************
        // GIVEN
        BidList bid = new BidList();
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        //String stringDate = "2007-11-11 12:13:14";
        //java.sql.Timestamp timeStamp = java.sql.Timestamp.valueOf(stringDate);
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //bid.setBidListDate(timeStamp);
        //bid.setCreationDate(timeStamp);
        // WHEN
        bidListService.addBid(bid);
        // THEN
        Assertions.assertNotNull(bid.getBidListId());
        Assertions.assertEquals("Account Test", bid.getAccount());
        Assertions.assertEquals(10d, bid.getBidQuantity());
        Assertions.assertEquals("Type Test", bid.getType());
        Assertions.assertNull(bid.getAskQuantity());
        //Assertions.assertEquals(timeStamp, bid.getBidListDate());
        //Assertions.assertEquals(timeStamp, bid.getCreationDate());

        // *************** UPDATE ***********************************
        // GIVEN
        bid.setBidQuantity(20d);
        bid.setAccount("update account");
        bid.setAskQuantity(10d);
        bid.setType("update type");
        // WHEN
        bidListService.updateBid(bid);
        // THEN
        Assertions.assertEquals(20d, bid.getBidQuantity());
        Assertions.assertEquals("update account", bid.getAccount());
        Assertions.assertEquals(10d, bid.getAskQuantity());
        Assertions.assertEquals("update type", bid.getType());

        // *************** CHECK IF EXISTS BY ID ***********************************
        // GIVEN
        Integer id = bid.getBidListId();
        // WHEN
        boolean checkIfIdExists = bidListService.checkIfIdExists(id);
        // THEN
        Assertions.assertTrue(checkIfIdExists);

        // *************** FIND BIDLIST BY ID ***********************************
        // GIVEN
        // WHEN
        Optional<BidList> bidList = Optional.ofNullable(bidListService.getBidByBidListId(id));
        // THEN
        //Assertions.assertEquals("update account", bidList.getAccount());
        Assertions.assertTrue(bidList.isPresent());

        // *************** FIND ALL ***********************************
        // WHEN
        List<BidList> listResult = bidListService.getAllBids();
        // THEN
        Assertions.assertTrue(listResult.size() > 0);

        // *************** DELETE ***********************************
        // GIVEN
        //Integer id3 = bid.getBidListId();
        // WHEN
        bidListService.deleteBidById(id);
        // THEN
        Optional<BidList> bidListDel = Optional.ofNullable(bidListService.getBidByBidListId(id));
        Assertions.assertFalse(bidListDel.isPresent());

    }

}
