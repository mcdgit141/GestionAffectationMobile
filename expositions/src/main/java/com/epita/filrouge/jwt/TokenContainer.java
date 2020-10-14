package com.epita.filrouge.jwt;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

public class TokenContainer {

    private final String jwtToken;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean defaultPassword;

    public TokenContainer(String jwtToken, Collection<? extends GrantedAuthority> authorities, boolean defaultPassword) {
        this.jwtToken = jwtToken;
        this.authorities = authorities;
        this.defaultPassword=defaultPassword;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public boolean isDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(boolean defaultPassword) {
        this.defaultPassword = defaultPassword;
    }
}
