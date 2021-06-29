package com.example.model.repository;

import com.example.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Interface to handle statements to user table in database
 *
 * @author Iryna Radchuk
 */
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserEmail(String email);
}
