package com.banking.thejavabanking.models.entity;

import com.banking.thejavabanking.dto.validators.EnumValue;
import com.banking.thejavabanking.models.Enums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
@Table(name = "tbl_user")
public class User extends BaseEntity implements Serializable {
    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "dob")
    LocalDate dob; //don't change in sql
//    Date dob;

    @NotNull
    @EnumValue(name = "gender", enumClass = Enums.Gender.class)
    @Column(name = "gender")
    String gender;

    @Column(name = "email")
    String email;

    @Column(
            name = "phone_number",
            unique = true
    )
    @Size(
            min = 10,
            max = 10
    )
    String phoneNumber;

    @Column(
            name = "password",
            nullable = false
    )
    String password;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    Account account;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    ForgotPassword forgotPassword;

    @OneToOne(
            targetEntity = Photo.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "photo_id")
    Photo photo;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "phone_token_id")
    @JsonIgnore
    PhoneToken phoneToken;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<Address> addresses = new HashSet<>();

    public void saveAddress(Address address) {
        if (address != null) {
            if (addresses == null) {
                addresses = new HashSet<>();
            }
            addresses.add(address);
            address.setUser(this);
        }
    }

    @JsonIgnore // Stop infinite loop
    public Set<Address> getAddresses() {
        return addresses;
    }
}
