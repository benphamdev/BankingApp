package com.banking.thejavabanking.domain.users.repositories.specification;

import com.banking.thejavabanking.domain.common.constants.Enums;
import com.banking.thejavabanking.domain.users.entities.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecificationBuilder {
    private final List<SpecSearchCriteria> params;

    public UserSpecificationBuilder() {this.params = new ArrayList<>();}

    public UserSpecificationBuilder with(
            String key, String operation, Object value, String prefix, String suffix
    ) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public UserSpecificationBuilder with(
            String isOrPredicate, String key, String operation, Object value, String prefix,
            String suffix
    ) {
        Enums.SearchOperation searchOperation =
                Enums.SearchOperation.getSimpleOperation(operation.charAt(0));
        if (searchOperation == Enums.SearchOperation.EQUALITY) {
            boolean startWithAsterisk =
                    prefix != null && prefix.contains(Enums.SearchOperation.ZERO_OR_MORE_REGEX);
            boolean endWithAsterisk =
                    suffix != null && suffix.contains(Enums.SearchOperation.ZERO_OR_MORE_REGEX);
            if (startWithAsterisk && endWithAsterisk) {
                searchOperation = Enums.SearchOperation.CONTAINS;
            } else if (startWithAsterisk) {
                searchOperation = Enums.SearchOperation.ENDS_WITH;
            } else if (endWithAsterisk) {
                searchOperation = Enums.SearchOperation.STARTS_WITH;
            } else {
                searchOperation = Enums.SearchOperation.EQUALITY;
            }
        } else {
            searchOperation = searchOperation;
        }
        params.add(new SpecSearchCriteria(isOrPredicate, key, searchOperation, value));
        return this;
    }

    public Specification<User> build() {
        if (params.isEmpty()) return null;

        Specification<User> result = new UserSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i)
                           .getIsOrPredicate()
                    ? Specification.where(result)
                                   .or(new UserSpecification(params.get(i)))
                    : Specification.where(result)
                                   .and(new UserSpecification(params.get(i)));
        }

        return result;
    }

}
