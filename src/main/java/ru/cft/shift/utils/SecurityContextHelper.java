package ru.cft.shift.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextHelper {
    public static String email(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static void setNotAuthenticated() {
        SecurityContextHolder.clearContext();
    }

    public static void setAuthenticated(Authentication auth){
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private SecurityContextHelper(){}
}
