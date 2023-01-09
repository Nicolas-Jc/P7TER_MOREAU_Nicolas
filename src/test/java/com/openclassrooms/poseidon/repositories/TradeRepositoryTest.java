package com.openclassrooms.poseidon.repositories;


import com.openclassrooms.poseidon.models.Trade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class TradeRepositoryTest {
    @Autowired
    private TradeRepository tradeRepository;

    @Test
    @DisplayName("TradeRepository ==>  Trade SAVE, UPDATE, FIND, DELETE - UNIT TESTS")
    void tradeTest() {
        // *************** SAVE ***********************************
        // GIVEN
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setBuyQuantity(55d);

        //String stringDate = "2007-11-11 12:13:14";
        //Timestamp timeSt = Timestamp.valueOf(stringDate);
        //Timestamp timeSt = new Timestamp(System.currentTimeMillis());
        //trade.setRevisionDate(timeSt);
        //trade.setCreationDate(timeSt);
        //trade.setTradeDate(timeSt);

        // WHEN
        trade = tradeRepository.save(trade);
        // THEN
        Assertions.assertNotNull(trade.getTradeId());
        Assertions.assertEquals("Trade Account", trade.getAccount());

        // *************** UPDATE ***********************************
        // GIVEN
        trade.setAccount("Trade Account Updated");
        trade.setType("Type Updated");

        // WHEN
        trade = tradeRepository.save(trade);
        // THEN
        Assertions.assertEquals("Trade Account Updated", trade.getAccount());
        Assertions.assertEquals("Type Updated", trade.getType());

        // *************** CHECK IF EXISTS BY ID ***********************************
        // GIVEN
        Integer id = trade.getTradeId();
        // WHEN
        boolean checkIfIdExists = tradeRepository.existsById(id);
        // THEN
        Assertions.assertTrue(checkIfIdExists);

        // *************** FIND ALL ***********************************
        // WHEN
        List<Trade> listResult = tradeRepository.findAll();
        // THEN
        Assertions.assertTrue(listResult.size() > 0);

        // *************** DELETE ***********************************
        // GIVEN
        //Integer id3 = curvePoint.getBidListId();
        // WHEN
        tradeRepository.deleteById(id);
        // THEN
        Optional<Trade> tradeDel = tradeRepository.findById(id);
        Assertions.assertFalse(tradeDel.isPresent());

    }

}
