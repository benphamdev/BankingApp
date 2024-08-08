package com.banking.thejavabanking.infrastructure.constants;

public interface AppConst {
    String SEARCH_OPERATOR = "(\\w+?)(:|<|>)(.*)";
    String SEARCH_SPEC_OPERATOR = "(\\w+?)([<:>~!])(.*)(\\p{Punct}?)(\\p{Punct}?)";
    String SORT_BY = "(\\w+?)(:)(.*)";
    String ADDRESS_REGEX = "address_";
    String LIKE_FORMAT = "%%%s%%";
}
