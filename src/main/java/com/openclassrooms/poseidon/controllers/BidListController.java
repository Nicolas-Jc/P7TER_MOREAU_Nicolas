package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.BidList;
import com.openclassrooms.poseidon.services.BidListService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;

    private static final Logger logger = LogManager.getLogger(BidListController.class);


    private static final String REDIRECT_TRANSAC = "redirect:/bidList/list";
    private static final String ATTRIB_NAME = "bidList";
    private static final String BID_NOT_EXISTS = "Bid {} not exists ! : ";


    // return view containing all Bids

    @GetMapping("/bidList/list")
    public String home(Model model, Principal principal) {
        model.addAttribute(ATTRIB_NAME, bidListService.getAllBids());
        model.addAttribute("username", principal.getName());
        logger.info("BidList Data loading");
        return "bidList/list";
    }


    // view bidList/add
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute(ATTRIB_NAME, new BidList());
        logger.info("View bidListAdd : OK");
        return "bidList/add";
    }

    // Button Add Bid To List
    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute(ATTRIB_NAME) BidList bidList,
                           BindingResult result, RedirectAttributes redirAttrs) {

        if (!result.hasErrors()) {
            bidListService.addBid(bidList);
            redirAttrs.addFlashAttribute("successSaveMessage", "Bid successfully added to list");
            return REDIRECT_TRANSAC;
        }
        logger.error("Error creation Bid");
        return "bidList/add";
    }


    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            if (!bidListService.checkIfIdExists(id)) {
                logger.error("GET /bidList/update : Non existent id");
                return REDIRECT_TRANSAC;
            }
            model.addAttribute(ATTRIB_NAME, bidListService.getBidByBidListId(id));
            logger.info("GET /bidList/update : OK");
        } catch (Exception e) {
            logger.error("/bidList/update/{id} : KO - Invalid bidList ID {} :", id);
        }
        return "bidList/update";

    }

    // Update Bid Button
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute(ATTRIB_NAME) BidList bidList,
                            BindingResult result, RedirectAttributes redirAttrs) {
        if (!bidListService.checkIfIdExists(id)) {
            logger.error(BID_NOT_EXISTS, id);
            return REDIRECT_TRANSAC;
        }
        if (!result.hasErrors()) {
            bidListService.updateBid(bidList);
            redirAttrs.addFlashAttribute("successUpdateMessage", "Bid successfully updated");
            return REDIRECT_TRANSAC;
        }
        logger.error("UPDATE Bid : KO");
        return "/bidList/update";
    }

    // Bid Delete Button
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, RedirectAttributes redirAttrs) {
        try {
            if (!bidListService.checkIfIdExists(id)) {
                logger.error(BID_NOT_EXISTS, id);
                return REDIRECT_TRANSAC;
            }
            bidListService.deleteBidById(id);
            model.addAttribute(ATTRIB_NAME, bidListService.getAllBids());
            redirAttrs.addFlashAttribute("successDeleteMessage", "DELETE Bid : OK");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("errorDeleteMessage", "Error during deletion");
            logger.error("Error to delete \"Bid \" : {}", id);
        }
        return REDIRECT_TRANSAC;
    }
}
