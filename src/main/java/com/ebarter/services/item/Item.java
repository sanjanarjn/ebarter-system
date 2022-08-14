package com.ebarter.services.item;

import com.ebarter.services.BaseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
public class Item extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id", nullable=false)
    private ItemCategory category;

    private String title;
    private long ownerId;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private JsonNode details;

    private ItemAvailabilityStatus availabilityStatus;

    private int points;

    @PrePersist
    public void modifyEntityBeforeCreation() {
        super.setCreatedTime();
        this.availabilityStatus = ItemAvailabilityStatus.AVAILABLE;
    }
}
