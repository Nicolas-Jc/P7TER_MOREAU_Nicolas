package com.openclassrooms.poseidon.repositories;


import com.openclassrooms.poseidon.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("UserRepository ==>  User SAVE, UPDATE, FIND, DELETE - UNIT TESTS")
    void userTest() {
        // *************** SAVE ***********************************
        // GIVEN
        User user = new User();
        user.setUsername("UserName");
        user.setPassword("MotdePasse09012023*");
        user.setFullname("FullName");
        user.setRole("ADMIN");
        // WHEN
        user = userRepository.save(user);
        // THEN
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("FullName", user.getFullname());

        // *************** UPDATE ***********************************
        // GIVEN
        user.setUsername("UserName Updated");
        user.setRole("USER");

        // WHEN
        user = userRepository.save(user);
        // THEN
        Assertions.assertEquals("UserName Updated", user.getUsername());
        Assertions.assertEquals("USER", user.getRole());

        // *************** CHECK IF EXISTS BY ID ***********************************
        // GIVEN
        Integer id = user.getId();
        // WHEN
        boolean checkIfIdExists = userRepository.existsById(id);
        // THEN
        Assertions.assertTrue(checkIfIdExists);

        // *************** CHECK IF EXISTS BY USERNAME ***********************************
        // WHEN
        boolean checkIfUserNameExists = userRepository.existsByUsername("UserName Updated");
        // THEN
        Assertions.assertTrue(checkIfUserNameExists);


        // *************** FIND BY USERNAME ***********************************
        // WHEN
        User userToFind = userRepository.findByUsername("UserName Updated");
        // THEN
        Assertions.assertEquals("USER", userToFind.getRole());


        // *************** FIND ALL ***********************************
        // WHEN
        List<User> listResult = userRepository.findAll();
        // THEN
        Assertions.assertTrue(listResult.size() > 0);

        // *************** DELETE ***********************************
        // GIVEN
        // WHEN
        userRepository.deleteById(id);
        // THEN
        Optional<User> tradeDel = userRepository.findById(id);
        Assertions.assertFalse(tradeDel.isPresent());
    }
}
