package com.cloudnative.webapi.config.auth;

import com.cloudnative.webapi.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User implements UserDetails {
    private final boolean emailVerified;

    public CustomUserDetails(User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.NO_AUTHORITIES);
        this.emailVerified = user.getAccountVerified();
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }
}
