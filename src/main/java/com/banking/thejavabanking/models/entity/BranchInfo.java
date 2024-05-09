package com.banking.thejavabanking.models.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_branch_info")
public class BranchInfo extends BaseEntity implements Serializable {
    @Column(name = "branch_name")
    String branchName;

    @Column(name = "address")
    String address;

    @ManyToOne
    @JoinColumn(name = "province_id")
    Province province;

    @OneToMany(
            mappedBy = "branchInfo",
            cascade = CascadeType.ALL
    )
    private List<Account> accounts = new ArrayList<>();
}
