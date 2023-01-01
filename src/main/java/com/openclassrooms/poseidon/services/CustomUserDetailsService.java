package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.models.UserModel;
import com.openclassrooms.poseidon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(userName);
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), Collections.singletonList(authority));
    }

}