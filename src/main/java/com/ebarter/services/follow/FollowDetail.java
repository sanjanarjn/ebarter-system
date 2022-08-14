package com.ebarter.services.follow;

import com.ebarter.services.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class FollowDetail extends BaseEntity {

    private long follower;
    private long followee;
}
