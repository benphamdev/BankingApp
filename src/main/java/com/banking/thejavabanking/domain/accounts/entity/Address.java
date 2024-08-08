package com.banking.thejavabanking.domain.accounts.entity;

import com.banking.thejavabanking.contract.abstractions.shared.DateTrackingBase;
import com.banking.thejavabanking.domain.users.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_address")
public class Address extends DateTrackingBase<Integer> implements Serializable {
    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(name = "floor")
    private String floor;

    @Column(name = "building")
    private String building;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "address_type")
    private Integer addressType;
}