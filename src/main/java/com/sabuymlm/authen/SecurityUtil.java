package com.sabuymlm.authen;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static AuthenUserDetails getUserDetails() { 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
        AuthenUserDetails userDetails = (AuthenUserDetails) authentication.getPrincipal();
        return userDetails; 
    }

    public static boolean isLoggedIn() { 
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
