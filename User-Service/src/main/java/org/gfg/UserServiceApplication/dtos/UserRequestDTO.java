package org.gfg.UserServiceApplication.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.gfg.Utilities.UserIdentifier;
import org.gfg.UserServiceApplication.model.UserType;
import org.gfg.UserServiceApplication.model.Users;

@Getter
@Setter
//@RequiredArgsConstructor
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private String name;
    @NotBlank(message = "contact can not be blank")
    private String contact;
    @NotBlank(message = "email can not be blank")
    private String email;
    private String address;
    private String dob;
    @NotNull(message = "userIdentifier can not be blank")
    private UserIdentifier userIdentifier;
    @NotBlank(message = "userIdentifierValue can not be blank")
    private String userIdentifierValue;
    @NotBlank(message = "password can not be blank")
    private String password;


    public Users toUser() {
        return Users.builder().
                name(this.name).
                contact(this.contact).
                email(this.email).
                address(this.address).
                dob(this.dob).
                userIdentifierValue(this.userIdentifierValue).
                identifier(this.userIdentifier).
                enabled(true).
                accountNonExpired(true).
                accountNonLocked(true).
                credentialsNonExpired(true).
                userType(UserType.USER).
                build();
    }

}
