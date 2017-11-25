package org.zen.zenmail.identity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication {
    private final String userName;
    private final String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean authenticated = true;

    public UserAuthentication(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String getName() {
        return userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public TokenUser getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userName;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
