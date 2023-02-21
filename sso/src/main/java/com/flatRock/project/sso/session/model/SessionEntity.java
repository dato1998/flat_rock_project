package com.flatRock.project.sso.session.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity {
    private String userId;
    private String username;
    private String email;
    private Long lastUpdatedDate;
}
