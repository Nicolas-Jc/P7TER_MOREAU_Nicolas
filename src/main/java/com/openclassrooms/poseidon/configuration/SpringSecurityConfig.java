package com.openclassrooms.poseidon.configuration;

import com.openclassrooms.poseidon.services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/bidList/**", "/rating/**", "/ruleName/**", "/trade/**", "/curvePoint/**")
                .hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/user/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                //.anyRequest().authenticated()
                //.and().formLogin().loginPage("/login").permitAll()
                .and()
                .formLogin().defaultSuccessUrl("/bidList/list")
                .and()
                .oauth2Login().defaultSuccessUrl("/bidList/list")
                .and()
                .logout().logoutUrl("/app-logout").logoutSuccessUrl("/")
                .and()
                .exceptionHandling().accessDeniedPage("/app/error");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}



        /*http.authorizeRequests()
                .antMatchers("/bidList/**", "/rating/**", "/ruleName/**", "/trade/**", "/curvePoint/**")
                .hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/user/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                //.anyRequest().authenticated()
                //.and().formLogin().loginPage("/login").permitAll()
                .and().formLogin()
                // configuration
                .defaultSuccessUrl("/bidList/list").and().logout() // logout configuration
                .logoutUrl("/app-logout").logoutSuccessUrl("/").and().exceptionHandling() // exception handling
                // configuration
                .accessDeniedPage("/app/error");
    }*/



