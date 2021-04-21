package com.thonecardoso.bookstoremanager.users.controller;

import com.thonecardoso.bookstoremanager.users.dto.JwtRequest;
import com.thonecardoso.bookstoremanager.users.dto.JwtResponse;
import com.thonecardoso.bookstoremanager.users.dto.MessageDTO;
import com.thonecardoso.bookstoremanager.users.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("System users management")
public interface UserControllerDocs {

    @ApiOperation(value = "User creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success user creation"),
            @ApiResponse(code = 400, message = "Missing required fields, or an error on validation fiel rules")
    })
    MessageDTO create(UserDTO userToCreateDTO);

    @ApiOperation(value = "User exclusion operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success user exclusion"),
            @ApiResponse(code = 404, message = "User with informed ID not found in the system")
    })
    void delete(Long id);

    @ApiOperation(value = "User update operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success user updated"),
            @ApiResponse(code = 400, message = "Missing required fields, or an error on validation fiel rules")
    })
    MessageDTO update(Long id, UserDTO userToUpdateDTO);

    @ApiOperation(value = "User authentication operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success user authenticated"),
            @ApiResponse(code = 404, message = "User not found")
    })
    JwtResponse createAuthenticationToken(JwtRequest jwtRequest);
}
