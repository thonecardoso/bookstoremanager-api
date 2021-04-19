package com.thonecardoso.bookstoremanager.users.builder;

import com.thonecardoso.bookstoremanager.users.dto.UserDTO;
import com.thonecardoso.bookstoremanager.users.enums.Gender;
import com.thonecardoso.bookstoremanager.users.enums.Role;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class UserDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Thone Cardoso";

    @Builder.Default
    private int age = 31;

    @Builder.Default
    private Gender gender = Gender.MALE;

    @Builder.Default
    private String email = "thone@teste.com";

    @Builder.Default
    private String username = "thonecardoso";

    @Builder.Default
    private String password = "123456";

    @Builder.Default
    private LocalDate birthdate = LocalDate.of(1989, 8, 21);

    @Builder.Default
    private Role role = Role.USER;

    public UserDTO buildUserDTO() {
        return new UserDTO(
                id,
                name,
                age,
                gender,
                email,
                username,
                password,
                birthdate,
                role
        );
    }
}
