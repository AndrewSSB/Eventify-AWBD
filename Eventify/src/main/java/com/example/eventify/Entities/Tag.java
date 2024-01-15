package com.example.eventify.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Tags")
public class Tag extends BaseEntity {
    @Column(name = "TagName")
    private String TagName;
}
