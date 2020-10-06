package com.epita.filrouge.jwt;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

public class TokenContainer {

    private final String jwtToken;
    private Collection<? extends GrantedAuthority> authorities;

    public TokenContainer(String jwtToken, Collection<? extends GrantedAuthority> authorities) {
        this.jwtToken = jwtToken;
        this.authorities = authorities;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
