package org.scm.Services.Interfaces;


import org.scm.Forms.UserForm;
import org.scm.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Component
public interface UserService {


    Optional<User> saveUser(UserForm userForm);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(UserForm userForm);

    void deleteUserById(String username);

    boolean isUserExist(String username);

    List<User> getAllUsers();

    User getUserByEmail(String email);


}
