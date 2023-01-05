package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.RuleModel;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import com.openclassrooms.poseidon.services.RuleNameService;
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
public class RuleNameController {

    private static final Logger logger = LogManager.getLogger(RuleNameController.class);
    private static final String ATTRIB_NAME = "ruleName";
    private static final String REDIRECT_TRANSAC = "redirect:/ruleName/list";
    private static final String RULE_NOT_EXISTS = "Rule {} not exists ! : ";

    @Autowired
    RuleNameRepository ruleNameRepository;

    @Autowired
    RuleNameService ruleNameService;

    // return view containing all Rules
    @GetMapping("/ruleName/list")
    //@RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute(ATTRIB_NAME, ruleNameService.getAllRuleNames());
        logger.info("Rules List Data loading");
        return "ruleName/list";
    }

    // view Rules/add
    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute(ATTRIB_NAME, new RuleModel());
        logger.info("View Rule Add loaded");
        return "ruleName/add";
    }

    // Button Add Rule To List
    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute(ATTRIB_NAME) RuleModel ruleName, BindingResult result, RedirectAttributes redirAttrs) {
        if (!result.hasErrors()) {
            ruleNameService.saveRuleName(ruleName);
            redirAttrs.addFlashAttribute("successSaveMessage", "Rule successfully added to list");
            return REDIRECT_TRANSAC;
        }
        logger.error("Error creation Rule");
        return "ruleName/add";
    }


    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            if (!ruleNameService.checkIfIdExists(id)) {
                logger.error("GET /ruleName/update : Non existent id");
                return REDIRECT_TRANSAC;
            }
            model.addAttribute(ATTRIB_NAME, ruleNameService.getRuleNameById(id));
            logger.info("GET /ruleName/update : OK");
        } catch (Exception e) {
            logger.error("/ruleName/update/{id} : KO - Invalid rule name ID {}", id);
        }
        return "ruleName/update";

    }

    // Update Rule Button
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid @ModelAttribute(ATTRIB_NAME) RuleModel ruleName,
                                 BindingResult result, RedirectAttributes redirAttrs) {
        if (!ruleNameService.checkIfIdExists(id)) {
            logger.error(RULE_NOT_EXISTS, id);
            return REDIRECT_TRANSAC;
        }
        if (!result.hasErrors()) {
            ruleNameService.saveRuleName(ruleName);
            redirAttrs.addFlashAttribute("successUpdateMessage", "Rule successfully updated");
            return REDIRECT_TRANSAC;
        }
        logger.error("UPDATE Rule : KO");
        return "ruleName/update";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model, RedirectAttributes redirAttrs) {
        try {
            if (!ruleNameService.checkIfIdExists(id)) {
                logger.error(RULE_NOT_EXISTS, id);
                return REDIRECT_TRANSAC;
            }
            ruleNameService.deleteRuleNameById(id);
            model.addAttribute(ATTRIB_NAME, ruleNameService.getAllRuleNames());
            redirAttrs.addFlashAttribute("successDeleteMessage", "Rule successfully deleted");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("errorDeleteMessage", "Error during deletion");
            logger.error("Error to delete \"Rule\" : {}", id);
        }
        return REDIRECT_TRANSAC;
    }
}
