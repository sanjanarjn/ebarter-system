package com.ebarter.services.profile;

import com.ebarter.services.BaseEntity;
import com.ebarter.services.user.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserProfile extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private int points;
    private float rating;
}
