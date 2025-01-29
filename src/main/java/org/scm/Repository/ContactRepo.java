package org.scm.Repository;

import org.scm.entities.Contact;
import org.scm.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public interface ContactRepo extends JpaRepository<Contact,String> {

    //Custom finder method
    List<Contact> findByUser(User user);

    //custom query method
    @Query("select c from Contact c where c.user.userId=:userId")
    List<Contact> findByUserId( @Param("userId") String id);

}
