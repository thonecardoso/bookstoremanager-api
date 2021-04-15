package com.thonecardoso.bookstoremanager.author.controller;

import com.thonecardoso.bookstoremanager.author.dto.AuthorDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api("Authors management")
public interface AuthorControllerDocs {

    @ApiOperation(value = "Author creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success method return"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or author already registered")
    })
    public AuthorDTO create(AuthorDTO authorDTO);
}
