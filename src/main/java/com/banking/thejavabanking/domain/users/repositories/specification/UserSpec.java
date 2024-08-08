package com.banking.thejavabanking.domain.users.repositories.specification;

import com.banking.thejavabanking.domain.users.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpec {
    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email);
    }

    public static Specification<User> hasFirstName(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
    }

    public static Specification<User> notEqualGender(String gender) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("gender"), gender));
    }
}
