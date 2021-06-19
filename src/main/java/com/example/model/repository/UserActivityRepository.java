package com.example.model.repository;

import com.example.model.UserActivity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserActivityRepository extends CrudRepository<UserActivity, Long> {
}
