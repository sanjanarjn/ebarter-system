package com.ebarter.services;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date createdTime;
    private Date modifiedTime;

    @PrePersist
    public void setCreatedTime() {
        this.createdTime = new Date();
        this.modifiedTime = new Date();
    }

    @PreUpdate
    public void setUpdatedTime() {
        this.modifiedTime = new Date();
    }
}
