package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.models.User;
import com.openclassrooms.poseidon.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(UserService.class);


    @Autowired
    public UserService(UserRepository userRep) {
        this.userRepository = userRep;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean checkIfUserExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean checkIfUserExistsById(int id) {
        return userRepository.existsById(id);
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public User getUserByEmail(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.info("User Id:{} was saved to User List", user.getId());

    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
        logger.info("Delete User : OK");
    }

}
