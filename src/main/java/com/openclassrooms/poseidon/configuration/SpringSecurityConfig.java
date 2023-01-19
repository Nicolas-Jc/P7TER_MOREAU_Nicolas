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
                //Permit access to all for the login and main home page
                .antMatchers("/", "/login").permitAll()
                //Permit access to /user/* to only users with Authority ADMIN
                .antMatchers("/user/*").hasAuthority("ADMIN")
                //Permit access for bootstrap
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/css/**").permitAll()
                //Authentication request parameters
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //Definition of the default Success URL
                .defaultSuccessUrl("/bidList/list")
                //.defaultSuccessUrl("/default", true).
                .and()
                //Configuration of the logout - Home return
                .logout().logoutUrl("/app-logout").logoutSuccessUrl("/")
                //Invalidate the current HTTP session and its cookies
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                //Definition of the default URL in case of exception of an access denied
                .exceptionHandling().accessDeniedPage("/error")
                .and()
                //oAuth2 Authentification with default access URL
                .oauth2Login().defaultSuccessUrl("/bidList/list");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
