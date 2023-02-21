package com.flatRock.project.productService.dtos;

import com.flatRock.project.productService.enums.Role;
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
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String phoneNumber;
    private Role role;
    private Date createdDate;

}
