package com.thonecardoso.bookstoremanager.users.controller;

import com.thonecardoso.bookstoremanager.users.dto.JwtRequest;
import com.thonecardoso.bookstoremanager.users.dto.JwtResponse;
import com.thonecardoso.bookstoremanager.users.dto.MessageDTO;
import com.thonecardoso.bookstoremanager.users.dto.UserDTO;
import com.thonecardoso.bookstoremanager.users.service.AuthenticationService;
import com.thonecardoso.bookstoremanager.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO create(@RequestBody @Valid UserDTO userToCreateDTO) {
        return userService.create(userToCreateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public MessageDTO update(@PathVariable Long id, @Valid @RequestBody UserDTO userToUpdateDTO) {
        return userService.update(id, userToUpdateDTO);
    }

    @PostMapping("/authenticate")
    public JwtResponse createAuthenticationToken(@Valid @RequestBody JwtRequest jwtRequest) {
        return authenticationService.createAuthenticationToken(jwtRequest);
    }
}
