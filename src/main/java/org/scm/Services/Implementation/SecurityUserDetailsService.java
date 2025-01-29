package org.scm.Services.Implementation;

import org.scm.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService  implements UserDetailsService {


    private final UserRepo userRepo;

    @Autowired
    SecurityUserDetailsService( UserRepo userRepo) {
        this.userRepo = userRepo;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load the user
        return userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("username not found"));
    }
}
