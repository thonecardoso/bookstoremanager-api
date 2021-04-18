package com.thonecardoso.bookstoremanager.service;

import com.thonecardoso.bookstoremanager.users.builder.UserDTOBuilder;
import com.thonecardoso.bookstoremanager.users.mapper.UserMapper;
import com.thonecardoso.bookstoremanager.users.repository.UserRepository;
import com.thonecardoso.bookstoremanager.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        UserDTOBuilder.builder().build();
    }


}
