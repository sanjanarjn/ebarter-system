package com.ebarter.services.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public boolean existsByEmail(String email);

    public User findByEmail(String email);
}
