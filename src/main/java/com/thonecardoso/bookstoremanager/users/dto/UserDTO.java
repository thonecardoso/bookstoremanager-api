package com.thonecardoso.bookstoremanager.users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thonecardoso.bookstoremanager.users.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotNull
    @Max(120)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender = Gender.MALE;

    @NotNull
    @NotEmpty
    @Email
    private String email = "thone@teste.com";

    @NotNull
    @NotEmpty
    private String username = "thonecardoso";

    @NotNull
    @NotEmpty
    private String password = "123456";

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthdate = LocalDate.of(1989, 8, 21);

    public UserDTO buildUserDTO(){
        return new UserDTO(
                id,
                name,
                age,
                gender,
                email,
                username,
                password,
                birthdate
        );
    }
}
