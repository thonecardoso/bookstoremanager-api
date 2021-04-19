package com.thonecardoso.bookstoremanager.users.controller;


import com.thonecardoso.bookstoremanager.users.builder.UserDTOBuilder;
import com.thonecardoso.bookstoremanager.users.dto.MessageDTO;
import com.thonecardoso.bookstoremanager.users.dto.UserDTO;
import com.thonecardoso.bookstoremanager.users.service.UserService;
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

import static com.thonecardoso.bookstoremanager.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final String USERS_API_URL_PATH = "/api/v1/users";
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();
        String expectedCreationMessage = "User thonecardoso with ID 1 successfully created";
        MessageDTO expectedCreationMessageDTO = MessageDTO.builder().message(expectedCreationMessage).build();

        when(userService.create(expectedUserToCreateDTO)).thenReturn(expectedCreationMessageDTO);

        mockMvc.perform(post(USERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedCreationMessage)));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenBadRequestStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();
        expectedUserToCreateDTO.setUsername(null);

        mockMvc.perform(post(USERS_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToCreateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenDELETEIsCalledThenNoContentStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToDeleteDTO = userDTOBuilder.buildUserDTO();

        Long expectedUserToDeleteDTOId = expectedUserToDeleteDTO.getId();
        doNothing().when(userService).delete(expectedUserToDeleteDTOId);

        mockMvc.perform(delete(USERS_API_URL_PATH + "/" + expectedUserToDeleteDTOId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    void whenPUTIsCalledThenOkStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToUpdateDTO = userDTOBuilder.buildUserDTO();
        expectedUserToUpdateDTO.setUsername("thoneupdate");
        String expectedUpdateMessage = "User thoneupdate with ID 1 successfully updated";
        MessageDTO expectedUpdateMessageDTO = MessageDTO.builder().message(expectedUpdateMessage).build();
        var expectedUserIdToUpdate = expectedUserToUpdateDTO.getId();

        when(userService.update(expectedUserIdToUpdate, expectedUserToUpdateDTO))
                .thenReturn(expectedUpdateMessageDTO);

        mockMvc.perform(put(USERS_API_URL_PATH + "/" + expectedUserIdToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedUpdateMessage)));
    }
}
