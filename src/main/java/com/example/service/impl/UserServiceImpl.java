package com.example.service.impl;

import com.example.exception.PasswordMismatchException;
import com.example.exception.RecordExistException;
import com.example.exception.TimeTrackerException;
import com.example.model.Role;
import com.example.model.RoleName;
import com.example.model.User;
import com.example.model.dto.AdminUserAddDTO;
import com.example.model.dto.AdminUserDTO;
import com.example.model.dto.UserRegisterDTO;
import com.example.model.dto.UserUpdateDto;
import com.example.model.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(UserRegisterDTO userRegisterDTO) {
        Optional<User> userFromDB = userRepository.findByUserEmail(userRegisterDTO.getUserEmail());
        if (userFromDB.isPresent()) {
            return false;
        }
        User user = new User(userRegisterDTO.getUserEmail(),
                passwordEncoder.encode(userRegisterDTO.getUserPassword()),
                userRegisterDTO.getUserFirstName(),
                userRegisterDTO.getUserLastName());
        userRepository.save(user);
        return true;
    }

    public void updateUser(UserUpdateDto userUpdateDto, Long id) throws TimeTrackerException {

        User user = userRepository.findById(id).orElseThrow();

        User userFromDB = userRepository.findByUserEmail(userUpdateDto.getUserEmail()).orElseThrow();
        if (Objects.nonNull(userFromDB) && !userFromDB.equals(user)) {

            throw new RecordExistException("This email is already in use");
        }
        if (!passwordEncoder.matches(userUpdateDto.getUserPasswordConfirm(), user.getUserPassword())) {
            throw new PasswordMismatchException("Password confirm does not match original user password");
        }


        user.setUserEmail(userUpdateDto.getUserEmail());
        user.setUserFirstName(userUpdateDto.getUserFirstName());
        user.setUserLastName(userUpdateDto.getUserLastName());
        user.setUserPassword(passwordEncoder.encode(userUpdateDto.getUserPassword()));
        userRepository.save(user);
    }

    @Override
    public Optional<User> userByIdSearch(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Iterable<User> usersFind() {
        return userRepository.findAll();
    }

    @Override
    public void userAddByAdmin(AdminUserAddDTO adminUserAddDTO) throws RecordExistException {
        Optional<User> userFromDB = userRepository.findByUserEmail(adminUserAddDTO.getUserAddEmail());
        if (userFromDB.isPresent()) {
            throw new RecordExistException("This email is already in use");
        }
        Role role = new Role(RoleName.valueOf(adminUserAddDTO.getRoleAdd()));
        User user = new User(adminUserAddDTO.getUserAddEmail(), adminUserAddDTO.getUserAddFirstName(), adminUserAddDTO.getUserAddLastName(), role);
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void userChangeByAdmin(AdminUserDTO adminUserDTO) throws RecordExistException {
        Optional<User> userFromDB = userRepository.findByUserEmail(adminUserDTO.getUserEmail());
        if (userFromDB.isPresent()) {
            throw new RecordExistException("This email is already in use");
        }
        User user = userRepository.findById(adminUserDTO.getId()).orElseThrow();
        user.setUserEmail(adminUserDTO.getUserEmail());
        user.setUserFirstName(adminUserDTO.getUserFirstName());
        user.setUserLastName(adminUserDTO.getUserLastName());
        user.setRole(new Role(RoleName.valueOf(adminUserDTO.getRoleList())));
        userRepository.save(user);
    }

}
