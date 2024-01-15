package com.example.eventify.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", updatable = false, nullable = false)
    private long Id;

    @Column(name = "CreatedAt", updatable = false)
    @CreationTimestamp
    private Date CreatedAt;

    @Column(name = "UpdatedAt")
    @UpdateTimestamp
    private Date UpdatedAt;

    @Column(name = "IsDeleted")
    private boolean IsDeleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity that)) return false;
        return Id == that.Id && CreatedAt.equals(that.CreatedAt) && UpdatedAt.equals(that.UpdatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "Id=" + Id +
                ", CreatedAt=" + CreatedAt +
                ", UpdatedAt=" + UpdatedAt +
                ", IsDeleted=" + IsDeleted +
                '}';
    }
}
