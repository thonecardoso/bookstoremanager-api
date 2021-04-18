package com.thonecardoso.bookstoremanager.users.builder;

import com.thonecardoso.bookstoremanager.users.enums.Gender;
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
    private Gender gender;

    @Builder.Default
    private String email;

    @Builder.Default
    private String username;

    @Builder.Default
    private String password;

    @Builder.Default
    private LocalDate birthdate;

}
