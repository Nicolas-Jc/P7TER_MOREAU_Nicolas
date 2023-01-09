package com.openclassrooms.poseidon.services;


import com.openclassrooms.poseidon.models.Trade;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class TradeServiceTest {
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private TradeService tradeService;

    @Test
    @DisplayName("TradeService ==>  Trade SAVE, UPDATE, FIND, DELETE - UNIT TESTS")
    void tradeTest() {
        // *************** SAVE ***********************************
        // GIVEN
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");
        trade.setBuyQuantity(55d);

        //String stringDate = "2007-11-11 12:13:14";
        //Timestamp timeSt = Timestamp.valueOf(stringDate);
        //trade.setRevisionDate(timeSt);
        //trade.setCreationDate(timeSt);
        //trade.setTradeDate(timeSt);
        // WHEN
        tradeService.addTrade(trade);
        // THEN
        Assertions.assertNotNull(trade.getTradeId());
        Assertions.assertEquals("Trade Account", trade.getAccount());

        // *************** UPDATE ***********************************
        // GIVEN
        trade.setAccount("Trade Account Updated");
        trade.setType("Type Updated");
        // WHEN
        tradeService.updateTrade(trade);
        // THEN
        Assertions.assertEquals("Trade Account Updated", trade.getAccount());
        Assertions.assertEquals("Type Updated", trade.getType());

        // *************** CHECK IF EXISTS BY ID ***********************************
        // GIVEN
        Integer id = trade.getTradeId();
        // WHEN
        boolean checkIfIdExists = tradeService.checkIfTradeIdExists(id);
        // THEN
        Assertions.assertTrue(checkIfIdExists);

        // *************** FIND ALL ***********************************
        // WHEN
        List<Trade> listResult = tradeService.getAllTrades();
        // THEN
        Assertions.assertTrue(listResult.size() > 0);

        // *************** DELETE ***********************************
        // GIVEN
        // WHEN
        tradeService.deleteTradeById(id);
        // THEN
        Optional<Trade> tradeDel = Optional.ofNullable(tradeService.getTradeById(id));
        Assertions.assertFalse(tradeDel.isPresent());

    }

}
