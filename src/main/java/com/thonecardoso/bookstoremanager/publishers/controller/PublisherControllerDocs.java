package com.thonecardoso.bookstoremanager.publishers.controller;

import com.thonecardoso.bookstoremanager.publishers.dto.PublisherDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api("Authors management")
public interface PublisherControllerDocs {

    @ApiOperation(value = "Publisher creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success publisher created"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or author already registered")
    })
    PublisherDTO create(PublisherDTO publisherDTO);

    @ApiOperation(value = "Find publisher by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success publisher found"),
            @ApiResponse(code = 404, message = "Publisher not found error code")
    })
    PublisherDTO findById(Long id);

    @ApiOperation(value = "Find all registered publisher")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success all registered publisher")
    })
    List<PublisherDTO> findAll();

    @ApiOperation(value = "Delete publisher by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success publisher deleted"),
            @ApiResponse(code = 404, message = "Publisher not found error code")
    })
    void delete(Long id);
}
