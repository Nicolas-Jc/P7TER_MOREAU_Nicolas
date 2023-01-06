package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.CurvePoint;
import com.openclassrooms.poseidon.repositories.CurvePointRepository;
import com.openclassrooms.poseidon.services.CurvePointService;
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
public class CurveController {

    @Autowired
    CurvePointRepository curvePointRepository;

    @Autowired
    CurvePointService curvePointService;

    private static final Logger logger = LogManager.getLogger(CurveController.class);
    private static final String ATTRIB_NAME = "curvePoint";
    private static final String CURVE_POINT_NOT_EXISTS = "CurvePoint {} not exists ! : ";
    private static final String REDIRECT_TRANSAC = "redirect:/curvePoint/list";


    // return view containing all CurvePoint
    @GetMapping("/curvePoint/list")
    //@RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute(ATTRIB_NAME, curvePointService.getAllCurvePoints());
        logger.info("CurvePoint List Data loading");
        return "curvePoint/list";
    }

    // view CurvePoint/add
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(Model model) {
        model.addAttribute(ATTRIB_NAME, new CurvePoint());
        logger.info("View CurvePoint Add loaded");
        return "curvePoint/add";
    }

    // Button Add CurvePoint To List
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute(ATTRIB_NAME) CurvePoint curvePoint, BindingResult result, RedirectAttributes redirAttrs) {

        if (!result.hasErrors()) {
            curvePointService.addCurvePoint(curvePoint);
            redirAttrs.addFlashAttribute("successSaveMessage", "CurvePoint successfully added to list");
            return REDIRECT_TRANSAC;
        }
        logger.error("Error creation CurvePoint");
        return "curvePoint/add";
    }


    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            if (!curvePointService.checkIfIdExists(id)) {
                logger.error("GET /curvePoint/update : Non existent id");
                return REDIRECT_TRANSAC;
            }
            model.addAttribute(ATTRIB_NAME, curvePointService.getCurvePointById(id));
            logger.info("GET /curvePoint/update : OK");
        } catch (Exception e) {
            logger.error("/curvePoint/update/{id} : KO - Invalid curve point ID {}", id);
        }
        return "curvePoint/update";
    }

    // Update CurvePoint Button
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute(ATTRIB_NAME) CurvePoint curvePoint,
                            BindingResult result, RedirectAttributes redirAttrs) {
        if (!curvePointService.checkIfIdExists(id)) {
            logger.error(CURVE_POINT_NOT_EXISTS, id);
            return REDIRECT_TRANSAC;
        }
        if (!result.hasErrors()) {
            curvePointService.updateCurvePoint(curvePoint);
            redirAttrs.addFlashAttribute("successUpdateMessage", "CurvePoint successfully updated");
            return REDIRECT_TRANSAC;
        }
        logger.error("UPDATE CurvePoint : KO");
        return "curvePoint/update";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model, RedirectAttributes redirAttrs) {
        try {
            if (!curvePointService.checkIfIdExists(id)) {
                logger.error(CURVE_POINT_NOT_EXISTS, id);
                return REDIRECT_TRANSAC;
            }
            curvePointService.deleteCurvePointById(id);
            model.addAttribute(ATTRIB_NAME, curvePointService.getAllCurvePoints());
            redirAttrs.addFlashAttribute("successDeleteMessage", "CurvePoint successfully deleted");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("errorDeleteMessage", "Error during deletion");
            logger.error("Error to delete \"CurvePoint\" : {}", id);
        }
        return REDIRECT_TRANSAC;
    }
}
