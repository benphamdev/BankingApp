package com.banking.thejavabanking.contract.abstractions.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serializable;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseAuditEntity<T extends Serializable> extends DateTrackingBase<T> implements Serializable {
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "is_deleted")
    @JsonIgnore
    @Builder.Default
    private Boolean isDeleted = false;
}
