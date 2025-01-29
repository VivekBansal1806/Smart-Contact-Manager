package org.scm.Services.Implementation;

import org.scm.Forms.UserForm;
import org.scm.Helper.Constraints;
import org.scm.Repository.UserRepo;
import org.scm.Services.Interfaces.UserService;
import org.scm.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {

    private final UserRepo userRepo;
    private final User user;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    UserServiceImp(UserRepo userRepo, User user) {
        this.userRepo = userRepo;
        this.user = user;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Optional<User> saveUser(UserForm userForm) {

        String userId= UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setName(userForm.getName());
        user.setPassword(userForm.getPassword());
        user.setEmail(userForm.getEmail());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setRolesList(List.of(Constraints.Role_user));
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setPhoneNumberEnabled(true);
        User savedUser = userRepo.save(user);
        return Optional.ofNullable(savedUser);
    }


    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(UserForm userForm) {

//
//
//        User user2=userRepo.findById(user.getUserId()).orElseThrow(()->new RuntimeException("User Not Found"));
//
//        BeanUtils.copyProperties(user,user2);
//        return  Optional.of(userRepo.save(user2));
return null;
    }

    @Override
    public void deleteUserById(String username) {

        userRepo.deleteById(username);
    }

    @Override
    public boolean isUserExist(String username) {

        return userRepo.existsById(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}
