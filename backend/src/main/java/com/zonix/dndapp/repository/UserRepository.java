package com.zonix.dndapp.repository;

import com.zonix.dndapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);;

    Optional<User> findBySessionTokenHash(String sessionTokenHash);

    @Query("""
        select u from User u
        where u.username = :identifier or u.email = :identifier
        """)
    Optional<User> findByEmailOrUsername(String identifier);
}
