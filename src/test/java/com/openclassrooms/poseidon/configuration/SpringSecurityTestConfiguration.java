package com.openclassrooms.poseidon.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@TestConfiguration
public class SpringSecurityTestConfiguration {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {

        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(User.withUsername("user").password("Password").roles("USER").build());
        userDetailsList.add(User.withUsername("admin").password("Password").roles("ADMIN", "USER").build());
        return new InMemoryUserDetailsManager(userDetailsList);

    }

}
