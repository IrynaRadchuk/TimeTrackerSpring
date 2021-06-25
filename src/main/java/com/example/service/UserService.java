package com.example.service;

import com.example.exception.RecordExistException;
import com.example.exception.TimeTrackerException;
import com.example.model.User;
import com.example.model.dto.AdminUserAddDTO;
import com.example.model.dto.AdminUserDTO;
import com.example.model.dto.UserRegisterDTO;
import com.example.model.dto.UserUpdateDto;

import java.util.Optional;

public interface UserService {
    boolean registerUser(UserRegisterDTO userRegisterDTO);

    void updateUser(UserUpdateDto userUpdateDto, Long id) throws TimeTrackerException;

    Optional<User> userByIdSearch(Long id);

    Iterable<User> usersFind();

    void userAddByAdmin(AdminUserAddDTO adminUserAddDTO) throws RecordExistException;

    void deleteUserById(Long id);

    void userChangeByAdmin(AdminUserDTO adminUserDTO) throws RecordExistException;
}
