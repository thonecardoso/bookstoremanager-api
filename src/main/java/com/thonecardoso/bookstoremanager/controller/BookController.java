package com.thonecardoso.bookstoremanager.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
@Api(value = "API Rest Books")
@CrossOrigin(origins = "*")
public class BookController {

    @ApiOperation(value = "Exemplo Swagger")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success method return")
    })
    @GetMapping
    public String hello(){
        return "Hello Bookstore Manager, I am running an example with PR!";
    }
}
