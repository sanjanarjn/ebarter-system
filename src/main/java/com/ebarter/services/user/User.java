package com.ebarter.services.user;

import com.ebarter.services.BaseEntity;
import com.ebarter.services.profile.UserProfile;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ToString(exclude = "profile")
public class User extends BaseEntity {

    private String email;
    private String password;
    private UserRole role;
    private boolean verified;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private UserProfile profile;

    private Date createdTime;
    private Date modifiedTime;

    public void setProfile(UserProfile profile) {
        if (profile == null) {
            if (this.profile != null) {
                this.profile.setUser(null);
            }
        }
        else {
            profile.setUser(this);
        }
        this.profile = profile;
    }
}

