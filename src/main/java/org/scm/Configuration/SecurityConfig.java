package org.scm.Configuration;

import org.scm.Services.Implementation.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final SecurityUserDetailsService securityUserDetailsService;
    private final OAuthenticationSuccessHandler successHandler;

    @Autowired
    SecurityConfig(SecurityUserDetailsService securityUserDetailsService, OAuthenticationSuccessHandler successHandler) {
        this.securityUserDetailsService = securityUserDetailsService;
        this.successHandler = successHandler;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User.withDefaultPasswordEncoder()
//                .username("admin123").
//                password("admin123").
//                roles("ADMIN").build();
//
//        UserDetails user2 = User.withDefaultPasswordEncoder().
//                username("admin321").
//                password("admin321").
//                roles("ADMIN").build();
//
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }


    // configuration of authentication provider for spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // object of user detail service
        daoAuthenticationProvider.setUserDetailsService(securityUserDetailsService);

        //object of password encoder
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        // urls configurations
//        httpSecurity.authorizeHttpRequests(authrize->authrize.requestMatchers("/home","/register").permitAll());


        httpSecurity.authorizeHttpRequests(auth ->
                auth.requestMatchers("/user/**")
                        .authenticated()
                        .anyRequest()
                        .permitAll());


        //form default login
//        httpSecurity.formLogin(Customizer.withDefaults());

        httpSecurity.formLogin(fm -> {
            fm.loginPage("/login");
            fm.loginProcessingUrl("/authenticate");
            fm.successForwardUrl("/user/profile");
            fm.failureForwardUrl("/login?error=true"); //URL in the browser	Stays the same
//            fm.failureUrl("/login?error=true");  //Parameters appear in the new HTTP request.
            fm.usernameParameter("email").
                    passwordParameter("password");
        });


        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(lg ->
                lg.logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
        );


        // configuration of oauth login
//        httpSecurity.oauth2Login(Customizer.withDefaults());


        httpSecurity.oauth2Login(oauth ->
                oauth.loginPage("/login")
                        .successHandler(successHandler)
                        .defaultSuccessUrl("/user/profile")
                        .failureUrl("/login?error=true")
        );

        return httpSecurity.build();
    }

}
