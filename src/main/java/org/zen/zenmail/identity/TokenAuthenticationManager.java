package org.zen.zenmail.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zen.zenmail.crypt.Hash;

@Service
public class TokenAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UserAuthentication readyTokenAuthentication = processAuthentication((UsernamePasswordAuthenticationToken) authentication);
                return readyTokenAuthentication;
            } else {
                authentication.setAuthenticated(false);
                return authentication;
            }
        } catch (Exception ex) {
            if (ex instanceof AuthenticationServiceException)
                throw ex;
        }
        return null;
    }

    private UserAuthentication processAuthentication(UsernamePasswordAuthenticationToken userAuthentication) {
        TokenUser user;
        try {
            user = userDetailsService.loadUserByUsername(userAuthentication.getName()).getUser();
        } catch (UsernameNotFoundException e) {
            throw new AuthenticationServiceException("user doesnt exist");
        }
        String plainPassword = (String) userAuthentication.getCredentials();
        String HashedPassword = Hash.hashWitDBSalt(plainPassword, user.getPassword());
        if (!HashedPassword.equals(user.getPassword())) {
            throw new AuthenticationServiceException("wrong passwords");
        }
        return buildToken(user.getUserName(), plainPassword);
    }

    private UserAuthentication buildToken(String name, String plainPassword) {
        return new UserAuthentication(name, plainPassword);
    }
}
