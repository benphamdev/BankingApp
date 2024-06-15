package com.banking.thejavabanking.models.abstractions;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class DateTrackingBase<T extends Serializable> extends BaseEntity<T> implements Serializable {
    @Column(name = "created_at")
//    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
//    @JsonIgnore
    @UpdateTimestamp
    private LocalDate modifiedAt;
}
