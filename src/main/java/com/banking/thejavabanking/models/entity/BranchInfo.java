package com.banking.thejavabanking.models.entity;

import com.banking.thejavabanking.models.abstractions.DateTrackingBase;
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
@Table(name = "tbl_branch_info")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class BranchInfo extends DateTrackingBase<Integer> implements Serializable {
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
