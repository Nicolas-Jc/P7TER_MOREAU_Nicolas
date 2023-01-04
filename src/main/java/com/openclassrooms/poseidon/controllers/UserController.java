package com.openclassrooms.poseidon.controllers;

import com.openclassrooms.poseidon.models.UserModel;
import com.openclassrooms.poseidon.repositories.UserRepository;
import com.openclassrooms.poseidon.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);
    private static final String REDIRECT_TRANSAC = "redirect:/user/list";
    private static final String ATTRIB_NAME = "users";
    private static final String USER_NOT_EXISTS = "User {} not exists ! : ";


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    //@RolesAllowed("ADMIN")
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        logger.info("User List Data loading");
        return "user/list";
    }

    //@RolesAllowed("ADMIN")
    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new UserModel());
        logger.info("View User Add Form loaded");
        return "user/add";
    }

    // Button Add User To List
    //@RolesAllowed("ADMIN")
    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute(ATTRIB_NAME) UserModel user, BindingResult result,
                           Model model, RedirectAttributes redirAttrs) {

        if (userService.checkIfUserExistsByUsername(user.getUsername())) {
            redirAttrs.addFlashAttribute("ErrorUserExistantMessage", "User is already registered");
            return "redirect:/user/add";
        }

        if (!result.hasErrors()) {
            userService.saveUser(user);
            redirAttrs.addFlashAttribute("successSaveMessage", "User successfully added to list");
            model.addAttribute("users", userService.getAllUsers());
            return REDIRECT_TRANSAC;
        }
        logger.info("Error creation User");
        return "user/add";
    }


    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            if (!userService.checkIfUserExistsById(id)) {
                return "redirect:/user/list";
            }
            UserModel user = userService.getUserById(id);
            user.setPassword("");
            model.addAttribute("user", user);
            logger.info("GET /user/update : OK");
        } catch (Exception e) {
            logger.info("/user/update : KO - Invalid user ID {}", id);
        }
        return "user/update";
    }

    // Update User Button
    //@RolesAllowed("ADMIN")
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute(ATTRIB_NAME) UserModel user,
                             BindingResult result, Model model, RedirectAttributes redirAttrs) {
        if (result.hasErrors()) {
            logger.info("UPDATE User : KO");
            return "user/update";
        }
        if (!userService.checkIfUserExistsById(id)) {
            logger.info(USER_NOT_EXISTS, id);
            return REDIRECT_TRANSAC;
        }

        userService.saveUser(user);
        model.addAttribute("user", userService.getAllUsers());
        redirAttrs.addFlashAttribute("successUpdateMessage", "User successfully updated");
        return REDIRECT_TRANSAC;
    }

    //@RolesAllowed("ADMIN")
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model,
                             RedirectAttributes redirAttrs) {
        try {
            if (!userService.checkIfUserExistsById(id)) {
                logger.info(USER_NOT_EXISTS, id);
                return REDIRECT_TRANSAC;
            }
            userService.deleteUserById(id);
            model.addAttribute("users", userService.getAllUsers());
            redirAttrs.addFlashAttribute("successDeleteMessage", "User successfully deleted");
        } catch (Exception e) {
            redirAttrs.addFlashAttribute("errorDeleteMessage", "Error during deletion");
            logger.info("Error to delete \"User\" : {}", id);
        }
        return REDIRECT_TRANSAC;
    }
}
