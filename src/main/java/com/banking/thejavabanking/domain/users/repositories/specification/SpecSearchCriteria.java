package com.banking.thejavabanking.domain.users.repositories.specification;

import com.banking.thejavabanking.domain.common.constants.Enums.SearchOperation;
import lombok.Getter;

@Getter
public class SpecSearchCriteria {
    private final String key;
    private final SearchOperation operation;
    private final Object value;
    private Boolean isOrPredicate;

    public SpecSearchCriteria(String key, SearchOperation operation, Object value) {
        super();
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SpecSearchCriteria(
            String orPredicate, String key, SearchOperation operation, Object value
    ) {
        super();
        this.isOrPredicate =
                orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG);
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SpecSearchCriteria(
            String key, String operation, String value, String prefix, String suffix
    ) {
        SearchOperation searchOperation = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (searchOperation != null && searchOperation == SearchOperation.EQUALITY) {
            boolean startWithAsterisk =
                    prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
            boolean endWithAsterisk =
                    suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
            if (startWithAsterisk && endWithAsterisk) {
                searchOperation = SearchOperation.CONTAINS;
            } else if (startWithAsterisk) {
                searchOperation = SearchOperation.ENDS_WITH;
            } else if (endWithAsterisk) {
                searchOperation = SearchOperation.STARTS_WITH;
            }
        }
        this.key = key;
        this.operation = searchOperation;
        this.value = value;
    }
}
