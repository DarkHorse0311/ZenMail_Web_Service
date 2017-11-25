package org.zen.zenmail.api.helpers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthHelper {
    public static boolean isLoggined(Authentication auth){
        return !(auth == null || (auth instanceof AnonymousAuthenticationToken) || !auth.isAuthenticated());
    }
}
