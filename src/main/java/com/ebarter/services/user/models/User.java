package com.ebarter.services.user.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String password;
    private UserRole role;
    private boolean verified;
    private Date createdTime;
    private Date modifiedTime;
}

