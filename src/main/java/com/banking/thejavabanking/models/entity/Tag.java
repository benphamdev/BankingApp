package com.banking.thejavabanking.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_tag")
public class Tag extends BaseEntity {
    private String name;
}
