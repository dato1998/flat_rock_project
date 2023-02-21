package com.flatRock.project.notificationService.data.dto;

import com.flatRock.project.notificationService.data.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phoneNumber;
    private Role role;
    private Date createdDate;

}
