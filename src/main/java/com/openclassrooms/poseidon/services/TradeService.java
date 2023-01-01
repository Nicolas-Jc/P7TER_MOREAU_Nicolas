package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.models.TradeModel;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TradeService {

    private static final Logger logger = LogManager.getLogger(TradeService.class);

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    public TradeService(TradeRepository tradeRep) {
        this.tradeRepository = tradeRep;
    }

    public boolean checkIfTradeIdExists(int id) {
        return tradeRepository.existsById(id);
    }

    public List<TradeModel> getAllTrades() {
        return tradeRepository.findAll();
    }

    public void addTrade(TradeModel trade) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        trade.setRevisionDate(timestamp);
        trade.setCreationDate(timestamp);
        trade.setTradeDate(timestamp);
        tradeRepository.save(trade);
        logger.info("Trade Id:{} was added to Trade List", trade.getTradeId());
    }

    public void updateTrade(TradeModel trade) {
        tradeRepository.save(trade);
        logger.info("UPDATE Trade : OK");
    }

    public void deleteTradeById(int id) {
        tradeRepository.deleteById(id);
        logger.info("Delete Trade : OK");
    }

    public TradeModel getTradeById(int id) {
        return tradeRepository.findById(id);
    }
}
