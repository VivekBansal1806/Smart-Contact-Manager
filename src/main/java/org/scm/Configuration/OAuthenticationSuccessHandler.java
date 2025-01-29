package org.scm.Configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.scm.Helper.Constraints;
import org.scm.Repository.UserRepo;
import org.scm.entities.Providers;
import org.scm.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger;
    private final UserRepo userRepo;

    @Autowired
    OAuthenticationSuccessHandler (UserRepo userRepo) {
        this.userRepo = userRepo;
        logger=LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthenticationSuccessHandler");

        // first we have to identify the provider

        var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String acrId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        logger.info("acrId={}",acrId);

        var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        // finding the attributes coming from oauth user

        logger.info("provider_user_id{}",oauthUser.getName());
        oauthUser.getAttributes().forEach((key, value) -> {
            System.out.println("key=" + key + " value=" + value);
        });

        User newUser=new User();
        newUser.setUserId(UUID.randomUUID().toString());
        newUser.setRolesList(List.of(Constraints.Role_user));
        newUser.setEmailVerified(true);
        newUser.setEnabled(true);

        //google
        if (acrId.equalsIgnoreCase("google")) {

            newUser.setEmail(oauthUser.getAttribute("email"));
            newUser.setProviders(Providers.GOOGLE);
            newUser.setProfilePic(oauthUser.getAttribute("picture"));
            newUser.setName(oauthUser.getAttribute("name"));
            newUser.setAbout("google user");
            newUser.setPassword("google123");
            newUser.setProviderId(Providers.GOOGLE.ordinal());

        }
        //github
        else if (acrId.equalsIgnoreCase("github")) {

            newUser.setName(oauthUser.getAttribute("name"));
            newUser.setProfilePic(oauthUser.getAttribute("avatar_url"));
            newUser.setAbout("github user");
            newUser.setProviders(Providers.GITHUB);
            newUser.setPassword("github123");
            newUser.setProviderId(Providers.GITHUB.ordinal());
        } else{
            logger.info("unknown provider");
        }


//        response.sendRedirect("/home");

//
//        logger.info(user.getAuthorities().toString());



        User isExist=userRepo.findByEmail(newUser.getEmail()).orElse(null);
        if(isExist==null) {
            userRepo.save(newUser);
            logger.info("User Saved");
        }
        new DefaultRedirectStrategy().sendRedirect(request, response, "/home");


    }
}
