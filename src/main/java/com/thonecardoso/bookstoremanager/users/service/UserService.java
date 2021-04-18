package com.thonecardoso.bookstoremanager.users.service;

import com.thonecardoso.bookstoremanager.users.dto.MessageDTO;
import com.thonecardoso.bookstoremanager.users.dto.UserDTO;
import com.thonecardoso.bookstoremanager.users.entity.User;
import com.thonecardoso.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.thonecardoso.bookstoremanager.users.mapper.UserMapper;
import com.thonecardoso.bookstoremanager.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO) {
        verifyIsExists(userToCreateDTO.getUsername(), userToCreateDTO.getEmail());
        User userToCreate = userMapper.toModel(userToCreateDTO);
        User createdUser = userRepository.save(userToCreate);
        return creationMessage(createdUser.getUsername(), createdUser.getId());
    }

    private void verifyIsExists(String username, String email) {
        Optional<User> foundUser = userRepository.findByUsernameOrEmail(username, email);
        if(foundUser.isPresent()){
            throw new UserAlreadyExistsException(username, email);
        }
    }

    private MessageDTO creationMessage(String createdUserName, Long createdUserId) {
        String createdUserMessage = String
                .format("User %s with ID %s successfully created", createdUserName, createdUserId);

        return MessageDTO.builder()
                .message(createdUserMessage)
                .build();
    }


}
