package com.ebarter.services.user;

import lombok.Data;

@Data
public class UserDTO {

    private long id;
    private String email;
    private String password;
    private UserRole role;
    private boolean verified;
}
