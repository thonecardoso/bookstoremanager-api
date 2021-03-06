package com.thonecardoso.bookstoremanager.users.service;

import com.thonecardoso.bookstoremanager.users.dto.MessageDTO;
import com.thonecardoso.bookstoremanager.users.dto.UserDTO;
import com.thonecardoso.bookstoremanager.users.entity.User;
import com.thonecardoso.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.thonecardoso.bookstoremanager.users.exception.UserNotFoundException;
import com.thonecardoso.bookstoremanager.users.mapper.UserMapper;
import com.thonecardoso.bookstoremanager.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.thonecardoso.bookstoremanager.users.utils.MessageDTOUtils.creationMessage;
import static com.thonecardoso.bookstoremanager.users.utils.MessageDTOUtils.updatedMessage;

@Service
public class UserService {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MessageDTO create(UserDTO userToCreateDTO) {
        verifyIsExists(userToCreateDTO.getUsername(), userToCreateDTO.getEmail());
        User userToCreate = userMapper.toModel(userToCreateDTO);
        userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));

        User createdUser = userRepository.save(userToCreate);
        return creationMessage(createdUser.getUsername(), createdUser.getId());
    }

    public MessageDTO update(Long id, UserDTO userToUpdateDTO) {
        User foundUser = verifyAngGetIfExists(id);
        User userToUpdate = userMapper.toModel(userToUpdateDTO);
        userToUpdate.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
        BeanUtils.copyProperties(userToUpdate, foundUser, "createdDate", "id");
        User updatedUser = userRepository.save(foundUser);
        return updatedMessage(updatedUser.getUsername(), updatedUser.getId());
    }

    public void delete(Long id){
        verifyAngGetIfExists(id);
        userRepository.deleteById(id);
    }

    private User verifyAngGetIfExists(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    }

    private void verifyIsExists(String username, String email) {
        Optional<User> foundUser = userRepository.findByUsernameOrEmail(username, email);
        if(foundUser.isPresent()){
            throw new UserAlreadyExistsException(username, email);
        }
    }


    public User verifyAndGetUserIfExists(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException(username));
    }
}
