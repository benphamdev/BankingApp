package com.banking.thejavabanking.domain.notifications.entites;

import com.banking.thejavabanking.contract.abstractions.shared.DateTrackingBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_tag")
public class Tag extends DateTrackingBase<Integer> {
    private String name;
}
