package com.thonecardoso.bookstoremanager.users.service;

import com.thonecardoso.bookstoremanager.users.mapper.UserMapper;
import com.thonecardoso.bookstoremanager.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
