package com.ebarter.services.user;

import com.ebarter.services.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class User extends BaseEntity {

    private String email;
    private String password;
    private UserRole role;
    private boolean verified;
    private Date createdTime;
    private Date modifiedTime;
}

