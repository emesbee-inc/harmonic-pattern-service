package com.emesbee.capm.harmonicpatternservice.security;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Generated;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Generated
@SuppressFBWarnings("NM_SAME_SIMPLE_NAME_AS_INTERFACE")
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final String username;

    public UserDetails(final String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
