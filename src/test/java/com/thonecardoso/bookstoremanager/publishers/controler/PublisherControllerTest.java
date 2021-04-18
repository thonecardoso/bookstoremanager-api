package com.thonecardoso.bookstoremanager.publishers.controler;

import com.thonecardoso.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.thonecardoso.bookstoremanager.publishers.controller.PublisherController;
import com.thonecardoso.bookstoremanager.publishers.dto.PublisherDTO;
import com.thonecardoso.bookstoremanager.publishers.service.PublisherService;
import com.thonecardoso.bookstoremanager.utils.JsonConversionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PublisherControllerTest {

    private final static String PUBLISHERS_API_URI_PATH = "/api/v1/publishers";

    private MockMvc mockMvc;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private PublisherController publisherController;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders
                .standaloneSetup(publisherController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.builderPublisherDTO();

        Mockito.when(publisherService.create(expectedCreatedPublisherDTO)).thenReturn(expectedCreatedPublisherDTO);

        mockMvc.perform(post(PUBLISHERS_API_URI_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedCreatedPublisherDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedCreatedPublisherDTO.getName())))
                .andExpect(jsonPath("$.code", is(expectedCreatedPublisherDTO.getCode())));


    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.builderPublisherDTO();
        expectedCreatedPublisherDTO.setName(null);

        mockMvc.perform(post(PUBLISHERS_API_URI_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETWithValidIdIsCalledThenStatusOkShouldBeReturned() throws Exception {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.builderPublisherDTO();

        Mockito.when(publisherService.findById(expectedPublisherFoundDTO.getId()))
                .thenReturn(expectedPublisherFoundDTO);

        mockMvc.perform(get(PUBLISHERS_API_URI_PATH + "/" + expectedPublisherFoundDTO.getId().intValue())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedPublisherFoundDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedPublisherFoundDTO.getName())))
                .andExpect(jsonPath("$.code", is(expectedPublisherFoundDTO.getCode())));
    }

    @Test
    void whenGETIsCalledThenStatusOkShouldBeReturned() throws Exception {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.builderPublisherDTO();

        Mockito.when(publisherService.findAll())
                .thenReturn(Collections.singletonList(expectedPublisherFoundDTO));

        mockMvc.perform(get(PUBLISHERS_API_URI_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedPublisherFoundDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(expectedPublisherFoundDTO.getName())))
                .andExpect(jsonPath("$[0].code", is(expectedPublisherFoundDTO.getCode())));
    }

    @Test
    void whenDELETEWithValidIdIsCalledThenNoContentShouldBeReturned() throws Exception {
        var expectedFoundPublisherDTO = publisherDTOBuilder.builderPublisherDTO();
        var expectedPublisherDeletedId = expectedFoundPublisherDTO.getId();

        doNothing().when(publisherService).delete(expectedPublisherDeletedId);

        mockMvc.perform(delete(PUBLISHERS_API_URI_PATH + "/" + expectedPublisherDeletedId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


}
