package com.ebarter.services.user;

import com.ebarter.services.user.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public boolean existsByEmail(String email);
}
