package com.flatRock.project.sso.model;

import com.flatRock.project.sso.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInformation {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private Long userId;
    private String token;
    private Role role;
}
