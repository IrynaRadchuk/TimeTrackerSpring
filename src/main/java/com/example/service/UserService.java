package com.example.service;

import com.example.exception.RecordExistException;
import com.example.exception.TimeTrackerException;
import com.example.model.User;
import com.example.model.dto.AdminUserAddDTO;
import com.example.model.dto.AdminUserDTO;
import com.example.model.dto.UserRegisterDTO;
import com.example.model.dto.UserUpdateDto;

import java.util.Optional;

/**
 * Service to manage users information
 *
 * @author Iryna Radchuk
 */
public interface UserService {

    /**
     * Create new user with registration
     */
    boolean registerUser(UserRegisterDTO userRegisterDTO);

    /**
     * Update user information
     */
    void updateUser(UserUpdateDto userUpdateDto, Long id) throws TimeTrackerException;

    /**
     * Get user information
     */
    Optional<User> userByIdSearch(Long id);

    /**
     * Get all users
     */
    Iterable<User> usersFind();

    /**
     * Add new user by administrator
     */
    void userAddByAdmin(AdminUserAddDTO adminUserAddDTO) throws RecordExistException;

    /**
     * Delete existing user by administrator
     */
    void deleteUserById(Long id);

    /**
     * Change existing user by administrator
     */
    void userChangeByAdmin(AdminUserDTO adminUserDTO) throws RecordExistException;
}
