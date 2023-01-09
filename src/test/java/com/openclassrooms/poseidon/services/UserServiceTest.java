package com.openclassrooms.poseidon.services;


import com.openclassrooms.poseidon.models.User;
import com.openclassrooms.poseidon.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("UserService ==>  User SAVE, UPDATE, FIND, DELETE - UNIT TESTS")
    void userTest() {
        // *************** SAVE ***********************************
        // GIVEN
        User user = new User();
        user.setUsername("UserName");
        user.setPassword("MotdePasse09012023*");
        user.setFullname("FullName");
        user.setRole("ADMIN");
        // WHEN
        userService.saveUser(user);
        // THEN
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("FullName", user.getFullname());

        // *************** UPDATE ***********************************
        // GIVEN
        user.setUsername("UserName Updated");
        user.setRole("USER");
        // WHEN
        userService.saveUser(user);
        // THEN
        Assertions.assertEquals("UserName Updated", user.getUsername());
        Assertions.assertEquals("USER", user.getRole());

        // *************** CHECK IF EXISTS BY ID ***********************************
        // GIVEN
        Integer id = user.getId();
        // WHEN
        boolean checkIfIdExists = userService.checkIfUserExistsById(id);
        // THEN
        Assertions.assertTrue(checkIfIdExists);

        // *************** CHECK IF EXISTS BY USERNAME ***********************************
        // WHEN
        boolean checkIfUserNameExists = userService.checkIfUserExistsByUsername("UserName Updated");
        // THEN
        Assertions.assertTrue(checkIfUserNameExists);


        // *************** FIND BY USERNAME ***********************************
        // WHEN
        User userToFind = userService.getUserByEmail("UserName Updated");
        // THEN
        Assertions.assertEquals("USER", userToFind.getRole());


        // *************** FIND ALL ***********************************
        // WHEN
        List<User> listResult = userService.getAllUsers();
        // THEN
        Assertions.assertTrue(listResult.size() > 0);

        // *************** DELETE ***********************************
        // GIVEN
        // WHEN
        userService.deleteUserById(id);
        // THEN
        Optional<User> tradeDel = Optional.ofNullable(userService.getUserById(id));
        Assertions.assertFalse(tradeDel.isPresent());
    }
}
