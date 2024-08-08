package com.banking.thejavabanking.domain.users.repositories.criteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchCriteriaConsumer implements Consumer<SearchCriteria> {
    private Predicate predicate;
    private CriteriaBuilder criteriaBuilder;
    private Root root;

    @Override
    public void accept(SearchCriteria searchCriteria) {
        if (searchCriteria.getOperation().equals(">")) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(
                    root.get(searchCriteria.getKey()), searchCriteria.getValue().toString()
            ));
        } else if (searchCriteria.getOperation().equals("<")) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(
                    root.get(searchCriteria.getKey()), searchCriteria.getValue().toString()
            ));
        } else if (searchCriteria.getOperation().equals(":")) {
            if (root.get(searchCriteria.getKey()).getJavaType() == String.class) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                        root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%"
                ));
            } else {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(
                        root.get(searchCriteria.getKey()), searchCriteria.getValue()
                ));
            }
        }
    }
}
