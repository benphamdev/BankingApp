package com.banking.thejavabanking.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_photo")
public class Photo extends BaseEntity implements Serializable {
    @Column(name = "public_id")
    @JsonIgnore
    String publicId;

    @Column(name = "url")
    String url;
}
