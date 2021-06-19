package com.example.model.repository;

import com.example.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    //User findByUserEmail (String email);
}
