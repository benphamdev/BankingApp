package com.banking.thejavabanking.models.entity;

import com.banking.thejavabanking.models.Enums.Provinces;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_province")
public class Province extends BaseEntity implements Serializable {
    @Enumerated(EnumType.STRING)
    @Column(
            length = 20,
            unique = true
    )
    Provinces name;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "province"
    )
    @JsonIgnore
    List<BranchInfo> branchInfos = new ArrayList<>();
}
