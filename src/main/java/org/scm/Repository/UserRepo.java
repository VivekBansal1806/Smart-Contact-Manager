package org.scm.Repository;

import org.scm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Component
public interface UserRepo extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);

}
