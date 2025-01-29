package org.scm.Helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class LoggedInUserHelper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

//        user login with oauth authentication
        if (authentication instanceof OAuth2AuthenticatedPrincipal) {
            String username = "";

            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            String acrId = token.getAuthorizedClientRegistrationId();

            var oauth2user = (OAuth2User) authentication.getPrincipal();

            // google
            if (acrId.equalsIgnoreCase("google")) {
                username = oauth2user.getAttribute("email");
            }

            //github
            else if (acrId.equalsIgnoreCase("github")) {
                username = oauth2user.getAttribute("email") != null ? oauth2user.getAttribute("email") : oauth2user.getAttribute("login") + "@gmail.com";

            } else
                System.out.println("UnIdentified : AuthorizedClientRegistrationId" + acrId);

            return username;
        }

        //self
        else
            return authentication.getName();
    }




}
