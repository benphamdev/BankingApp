package com.banking.thejavabanking.domain.accounts.entity;

import com.banking.thejavabanking.contract.abstractions.shared.BaseEntity;
import com.banking.thejavabanking.domain.common.constants.Enums.Provinces;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tbl_province")
public class Province extends BaseEntity<Integer> implements Serializable {
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
