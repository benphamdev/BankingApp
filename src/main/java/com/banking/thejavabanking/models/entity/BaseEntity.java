package com.banking.thejavabanking.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    Integer id;

    @Column(name = "created_at")
    @JsonIgnore
    @CreationTimestamp
    LocalDate createdAt;

    @Column(name = "modified_at")
    @JsonIgnore
    @UpdateTimestamp
    LocalDate modifiedAt;

    @Column(name = "is_active")
    @JsonIgnore
    @Builder.Default
    Boolean isActive = true;

    @Column(name = "is_deleted")
    @JsonIgnore
    @Builder.Default
    Boolean isDeleted = false;
}
