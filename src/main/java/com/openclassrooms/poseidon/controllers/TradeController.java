package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.TradeModel;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import com.openclassrooms.poseidon.services.TradeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class TradeController {
    private static final Logger logger = LogManager.getLogger(TradeController.class);
    private static final String ATTRIB_NAME = "trade";
    private static final String REDIRECT_TRANSAC = "redirect:/trade/list";
    private static final String TRADE_NOT_EXISTS = "Trade {} not exists ! : ";


    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    TradeService tradeService;

    // return view containing all Trades
    @GetMapping("/trade/list")
    //@RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute(ATTRIB_NAME, tradeService.getAllTrades());
        logger.info("Trade List Data loading");
        return "trade/list";
    }

    // view Trade/add
    @GetMapping("/trade/add")
    public String addTradeForm(Model model) {
        model.addAttribute(ATTRIB_NAME, new TradeModel());
        logger.info("View Trade Add loaded");
        return "trade/add";
    }

    // Button Add Trade To List
    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute(ATTRIB_NAME) TradeModel trade, BindingResult result, RedirectAttributes redirAttrs) {
        if (!result.hasErrors()) {
            tradeService.addTrade(trade);
            redirAttrs.addFlashAttribute("successSaveMessage", "Trade successfully added to list");
            return REDIRECT_TRANSAC;
        }
        logger.info("Error creation Trade");
        return "trade/add";
    }


    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            if (!tradeService.checkIfTradeIdExists(id)) {
                logger.info("GET /trade/update : Non existent id");
                return "redirect:/trade/list";
            }
            model.addAttribute("trade", tradeService.getTradeById(id));
            logger.info("GET /trade/update : OK");
        } catch (Exception e) {
            logger.info("GET /trade/update : KO - Invalid trade ID {}", id);
        }
        return "trade/update";

    }

    // Update Trade Button
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid @ModelAttribute(ATTRIB_NAME) TradeModel trade,
                              BindingResult result, RedirectAttributes redirAttrs) {
        if (!tradeService.checkIfTradeIdExists(id)) {
            logger.info(TRADE_NOT_EXISTS, id);
            return REDIRECT_TRANSAC;
        }
        if (!result.hasErrors()) {
            tradeService.updateTrade(trade);
            redirAttrs.addFlashAttribute("successUpdateMessage", "Trade successfully updated");
            return REDIRECT_TRANSAC;
        }
        logger.info("UPDATE Trade : KO");
        return REDIRECT_TRANSAC;
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model, RedirectAttributes redirAttrs) {
        try {
            if (!tradeService.checkIfTradeIdExists(id)) {
                logger.info(TRADE_NOT_EXISTS, id);
                return REDIRECT_TRANSAC;
            }
            tradeService.deleteTradeById(id);
            model.addAttribute(ATTRIB_NAME, tradeService.getAllTrades());
            redirAttrs.addFlashAttribute("successDeleteMessage", "Trade successfully deleted");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("errorDeleteMessage", "Error during deletion");
            logger.info("Error to delete \"Trade\" : {}", id);
        }
        return REDIRECT_TRANSAC;
    }
}