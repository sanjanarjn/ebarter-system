package com.ebarter.services.item;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Data
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonType.class)
})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String category;
    private String title;

    private long ownerId;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private JsonNode details;
}
