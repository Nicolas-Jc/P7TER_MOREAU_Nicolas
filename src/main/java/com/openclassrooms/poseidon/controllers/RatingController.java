package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.RatingModel;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import com.openclassrooms.poseidon.services.RatingService;
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
public class RatingController {

    private static final Logger logger = LogManager.getLogger(RatingController.class);

    private static final String ATTRIB_NAME = "rating";
    private static final String REDIRECT_TRANSAC = "redirect:/rating/list";
    private static final String RATING_NOT_EXISTS = "Rating {} not exists ! : ";


    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    RatingService ratingService;


    // return view containing all Ratings
    @GetMapping("/rating/list")
    //@RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute(ATTRIB_NAME, ratingService.getAllRatings());
        logger.info("Rating List Data loading");
        return "rating/list";
    }

    // view Rating/add
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute(ATTRIB_NAME, new RatingModel());
        logger.info("View Rating Add loaded");
        return "rating/add";
    }

    // Button Add Rating To List
    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute(ATTRIB_NAME) RatingModel rating, BindingResult result, RedirectAttributes redirAttrs) {

        if (!result.hasErrors()) {
            ratingService.saveRating(rating);
            redirAttrs.addFlashAttribute("successSaveMessage", "Rating successfully added to list");
            return REDIRECT_TRANSAC;
        }
        logger.info("Error creation Rating");
        return "rating/add";
    }


    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            if (!ratingService.checkIfIdExists(id)) {
                logger.info("GET /rating/update : Non existent id");
                return "redirect:/rating/list";
            }
            model.addAttribute("rating", ratingService.getRatingById(id));
            logger.info("GET /rating/update : OK");
        } catch (Exception e) {
            logger.info("GET /update/{id} : KO - Invalid rating ID {}", id);
        }
        return "rating/update";

    }


    // Update Rating Button
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid @ModelAttribute(ATTRIB_NAME) RatingModel rating,
                               BindingResult result, RedirectAttributes redirAttrs) {
        if (!ratingService.checkIfIdExists(id)) {
            logger.info(RATING_NOT_EXISTS, id);
            return REDIRECT_TRANSAC;
        }
        if (!result.hasErrors()) {
            ratingService.saveRating(rating);
            redirAttrs.addFlashAttribute("successUpdateMessage", "Rating successfully updated");
            return REDIRECT_TRANSAC;
        }
        logger.info("UPDATE Rating : KO");
        return REDIRECT_TRANSAC;
    }


    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model, RedirectAttributes redirAttrs) {
        try {
            if (!ratingService.checkIfIdExists(id)) {
                logger.info(RATING_NOT_EXISTS, id);
                return REDIRECT_TRANSAC;
            }
            ratingService.deleteRatingById(id);
            model.addAttribute(ATTRIB_NAME, ratingService.getAllRatings());
            redirAttrs.addFlashAttribute("successDeleteMessage", "Rating successfully deleted");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("errorDeleteMessage", "Error during deletion");
            logger.info("Error to delete \"Rating\" : {}", id);
        }
        return REDIRECT_TRANSAC;
    }

}