package com.market.member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class AuthUserPrincipal implements UserDetails, OAuth2User {

    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    public AuthUserPrincipal(String email,
                             String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this(email, password, authorities, Collections.emptyMap());
    }

    public AuthUserPrincipal(String email,
                             String password,
                             Collection<? extends GrantedAuthority> authorities,
                             Map<String, Object> attributes) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
