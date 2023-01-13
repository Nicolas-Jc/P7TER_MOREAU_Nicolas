package com.openclassrooms.poseidon.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private static final Logger logger = LogManager.getLogger(CustomOAuth2User.class);

    private OAuth2User oauth2User;

    public CustomOAuth2User(OAuth2User user) {
        this.oauth2User = user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        Map<String, Object> attributs = getAttributes();
        String pseudo = (String) attributs.get("login");
        logger.info("pseudo oAuth2 = {}", pseudo);
        return pseudo;
    }
}
