package com.banking.thejavabanking.repositories.search;

import com.banking.thejavabanking.dto.respones.shared.PageResponse;
import com.banking.thejavabanking.models.entity.Account;
import com.banking.thejavabanking.models.entity.Address;
import com.banking.thejavabanking.models.entity.User;
import com.banking.thejavabanking.repositories.criteria.SearchCriteria;
import com.banking.thejavabanking.repositories.criteria.UserSearchCriteriaConsumer;
import com.banking.thejavabanking.repositories.specification.SpecSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.banking.thejavabanking.utils.AppConst.*;

@Repository
public class SearchRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PageResponse<?> getAllUsersWithPagingAndSorting(
            int pageNo, int pageSize, String search, String sortBy
    ) {
//        StringBuilder builder = new StringBuilder("SELECT u FROM User u WHERE 1=1");

        // if you want to use DTO, you can use this query. If 1 field is null, it will return null
        StringBuilder builder = new StringBuilder(
                "SELECT new com.banking.thejavabanking.dto.respones.UserResponse(u.id, u.email, u.firstName, u.lastName, u.otherName, u.email) FROM User u WHERE 1=1"
        );
        if (StringUtils.hasText(search)) {
            builder.append(" AND lower(u.firstName) LIKE lower(:firstName)");
            builder.append(" OR lower(u.lastName) LIKE lower(:lastName)");
            builder.append(" OR lower(u.email) LIKE lower(:email)");
        }

        if (StringUtils.hasText(sortBy)) {
            // firstName:asc|desc
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String field = matcher.group(1);
                String order = matcher.group(3);
                builder.append(String.format(" ORDER BY u.%s %s", field, order));
            }
        }

        Query selectQuery = entityManager.createQuery(builder.toString());

        if (StringUtils.hasText(search)) {
            selectQuery.setParameter("firstName", String.format(LIKE_FORMAT, search));
            selectQuery.setParameter("lastName", String.format(LIKE_FORMAT, search));
            selectQuery.setParameter("email", String.format(LIKE_FORMAT, search));
        }
        selectQuery.setFirstResult(pageNo);
        selectQuery.setMaxResults(pageSize);
        List<?> users = selectQuery.getResultList();
        System.out.println(users);

        StringBuilder countBuilder = new StringBuilder("SELECT COUNT(*) FROM User u");
        if (StringUtils.hasText(search)) {
            countBuilder.append(" WHERE lower(u.firstName) LIKE lower(?1)");
            countBuilder.append(" OR lower(u.lastName) LIKE lower(?2)");
            countBuilder.append(" OR lower(u.email) LIKE lower(?3)");
        }
        Query countQuery = entityManager.createQuery(countBuilder.toString());
        if (StringUtils.hasText(search)) {
            countQuery.setParameter(1, String.format(LIKE_FORMAT, search));
            countQuery.setParameter(2, String.format(LIKE_FORMAT, search));
            countQuery.setParameter(3, String.format(LIKE_FORMAT, search));
        }
        Long totalElements = (Long) countQuery.getSingleResult();
        System.out.println(totalElements);

        // khong duoc
//        List<Sort.Order> sorts = new ArrayList<>();
//        if (StringUtils.hasLength(sortBy)) {
//            // firstName:asc|desc
//            Pattern pattern = Pattern.compile(SORT_BY);
//            Matcher matcher = pattern.matcher(sortBy);
//            if (matcher.find()) {
//                String field = matcher.group(1);
//                String order = matcher.group(3);
//                sorts.add(new Sort.Order(Sort.Direction.fromString(order), field));
//            }
//        }
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sorts));

        Page<?> page = new PageImpl<>(users, PageRequest.of(pageNo, pageSize), totalElements);
        return PageResponse.builder()
                           .page(pageNo)
                           .size(pageSize)
//                           .total(totalElements.intValue() / pageSize)
                           .total(page.getTotalPages())
//                           .items(users)
                           .items(page.stream()
                                      .toList())
                           .build();
    }

    public PageResponse<?> advancedSearchUser(
            int pageNo, int pageSize, String sortBy, String address, String... search
    ) {
        List<SearchCriteria> criteriaList = new ArrayList<>();
        // get list user
        if (search != null) {
            Pattern pattern = Pattern.compile(SEARCH_OPERATOR);
            for (String s : search) {
                // firstName:asc|desc
                Matcher matcher = pattern.matcher(s);
                if (matcher.find())
                    criteriaList.add(
                            new SearchCriteria(
                                    matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3)
                            ));
            }
        }

        // get number of record
        List<User> users = getUsers(pageNo, pageSize, criteriaList, sortBy, address);
        Long getTotalElements = getTotalElements(criteriaList, address);
        // return PageResponse
        return PageResponse.builder()
                           .page(pageNo) // offset = index of record in database
                           .size(pageSize)
                           .total(getTotalElements.intValue()) // total element
                           .items(users)
                           .build();
    }

    private List<User> getUsers(
            int pageNo, int pageSize, List<SearchCriteria> criteriaList, String sortBy,
            String address
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        // solve condition search
        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchCriteriaConsumer searchConsumer = new UserSearchCriteriaConsumer(
                predicate,
                criteriaBuilder,
                root
        );

        if (StringUtils.hasText(address)) {
            Join<Account, User> accountUserJoin = root.join("account");
            Predicate addressPredicate = criteriaBuilder.like(
                    accountUserJoin.get("accountNumber"),
                    "%" + address + "%"
            );
            // search all field of account

            query.where(predicate, addressPredicate);
        } else {
            criteriaList.forEach(searchConsumer);
            predicate = searchConsumer.getPredicate();
            query.where(predicate);
        }

        // solve sort by
        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                String field = matcher.group(1);
                String order = matcher.group(3);
                if (order.equals("asc")) {
                    query.orderBy(criteriaBuilder.asc(root.get(field)));
                } else {
                    query.orderBy(criteriaBuilder.desc(root.get(field)));
                }
            }
        }

        return entityManager.createQuery(query)
                            .setFirstResult(pageNo)
                            .setMaxResults(pageSize)
                            .getResultList();
    }

    private Long getTotalElements(List<SearchCriteria> criteriaList, String address) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<User> root = query.from(User.class);

        // solve condition search
        Predicate predicate = criteriaBuilder.conjunction();
        UserSearchCriteriaConsumer searchConsumer = new UserSearchCriteriaConsumer(
                predicate,
                criteriaBuilder,
                root
        );

        if (StringUtils.hasText(address)) {
            Join<Account, User> accountUserJoin = root.join("account");
            Predicate addressPredicate = criteriaBuilder.like(
                    accountUserJoin.get("accountNumber"),
                    "%" + address + "%"
            );
            // search all field of account
            query.select(criteriaBuilder.count(root));
            query.where(predicate, addressPredicate);
        } else {
            criteriaList.forEach(searchConsumer);
            predicate = searchConsumer.getPredicate();
            query.select(criteriaBuilder.count(root));
            query.where(predicate);
        }
        return entityManager.createQuery(query)
                            .getSingleResult();
    }

    public PageResponse<?> getUsersJoinedAddress(
            Pageable pageable, String[] users, String[] address
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Join<Address, User> addressUserJoin = root.join("addresses");

        // build query
        List<Predicate> userPredicates = createPredicatesFromStrings(root, criteriaBuilder, users);
        List<Predicate> addressPredicates = createPredicatesFromStrings(addressUserJoin, criteriaBuilder, address);

//        Pattern pattern = Pattern.compile(SEARCH_SPEC_OPERATOR);
//        for (String user : users) {
//            Matcher matcher = pattern.matcher(user);
//            if (matcher.find()) {
//                SpecSearchCriteria specSearchCriteria = new SpecSearchCriteria(
//                        matcher.group(1),
//                        matcher.group(2),
//                        matcher.group(3),
//                        matcher.group(4),
//                        matcher.group(5)
//                );
//                Predicate userPredicate = (Predicate) toPredicate(root, criteriaBuilder, specSearchCriteria);
//            }
//        }
//
//        for (String add : address) {
//            Matcher matcher = pattern.matcher(add);
//            if (matcher.find()) {
//                SpecSearchCriteria specSearchCriteria = new SpecSearchCriteria(
//                        matcher.group(1),
//                        matcher.group(2),
//                        matcher.group(3),
//                        matcher.group(4),
//                        matcher.group(5)
//                );
//                Predicate addressPredicate = (Predicate) toPredicate(addressUserJoin, criteriaBuilder, specSearchCriteria);
//            }
//        }

//        Predicate userPredicateArr = criteriaBuilder.or(userPredicates.toArray(new Predicate[0]));
        Predicate userPredicateArr = criteriaBuilder.and(userPredicates.toArray(new Predicate[0]));
        Predicate addressPredicateArr = criteriaBuilder.or(addressPredicates.toArray(new Predicate[0]));
        Predicate finalPredicate = criteriaBuilder.and(userPredicateArr, addressPredicateArr);

        query.where(finalPredicate);

        List<User> usersList = entityManager.createQuery(query)
                                            .setFirstResult(pageable.getPageNumber())
                                            .setMaxResults(pageable.getPageSize())
                                            .getResultList();

        int totalElements = entityManager.createQuery(query)
                                         .getResultList()
                                         .size();

        return PageResponse.builder()
                           .page(pageable.getPageNumber())
                           .size(pageable.getPageSize())
                           .total(totalElements)
                           .items(usersList)
                           .build();
    }

    //    public Predicate toPredicate(Root<User> root, CriteriaBuilder criteriaBuilder, SpecSearchCriteria criteria) {
//        return switch (criteria.getOperation()) {
//            case EQUALITY -> criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
//            case NEGATION -> criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue());
//            case GREATER_THAN -> criteriaBuilder.greaterThan(
//                    root.get(criteria.getKey()),
//                    criteria.getValue()
//                            .toString()
//            );
//            case LESS_THAN -> criteriaBuilder.lessThan(
//                    root.get(criteria.getKey()),
//                    criteria.getValue()
//                            .toString()
//            );
//            case LIKE -> criteriaBuilder.like(
//                    root.get(criteria.getKey()),
//                    "%" + criteria.getValue()
//                                  .toString() + "%"
//            );
//            case STARTS_WITH -> criteriaBuilder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
//            case ENDS_WITH -> criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
//            case CONTAINS -> criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
//            default -> throw new IllegalArgumentException("Invalid operation");
//        };
//    }
//
//    public Predicate toPredicate(Join<Address, User> root, CriteriaBuilder criteriaBuilder, SpecSearchCriteria criteria) {
//        return switch (criteria.getOperation()) {
//            case EQUALITY -> criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
//            case NEGATION -> criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue());
//            case GREATER_THAN -> criteriaBuilder.greaterThan(
//                    root.get(criteria.getKey()),
//                    criteria.getValue()
//                            .toString()
//            );
//            case LESS_THAN -> criteriaBuilder.lessThan(
//                    root.get(criteria.getKey()),
//                    criteria.getValue()
//                            .toString()
//            );
//            case LIKE -> criteriaBuilder.like(
//                    root.get(criteria.getKey()),
//                    "%" + criteria.getValue()
//                                  .toString() + "%"
//            );
//            case STARTS_WITH -> criteriaBuilder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
//            case ENDS_WITH -> criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
//            case CONTAINS -> criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
//            default -> throw new IllegalArgumentException("Invalid operation");
//        };
//    }

    public <T, U> Predicate toPredicate(Path<T> path, CriteriaBuilder criteriaBuilder, SpecSearchCriteria criteria) {
        return switch (criteria.getOperation()) {
            case EQUALITY -> criteriaBuilder.equal(path.get(criteria.getKey()), criteria.getValue());
            case NEGATION -> criteriaBuilder.notEqual(path.get(criteria.getKey()), criteria.getValue());
            case GREATER_THAN -> criteriaBuilder.greaterThan(
                    path.get(criteria.getKey()),
                    criteria.getValue()
                            .toString()
            );
            case LESS_THAN -> criteriaBuilder.lessThan(
                    path.get(criteria.getKey()),
                    criteria.getValue()
                            .toString()
            );
            case LIKE -> criteriaBuilder.like(
                    path.get(criteria.getKey()),
                    "%" + criteria.getValue()
                                  .toString() + "%"
            );
            case STARTS_WITH -> criteriaBuilder.like(path.get(criteria.getKey()), criteria.getValue() + "%");
            case ENDS_WITH -> criteriaBuilder.like(path.get(criteria.getKey()), "%" + criteria.getValue());
            case CONTAINS -> criteriaBuilder.like(path.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            default -> throw new IllegalArgumentException("Invalid operation");
        };
    }

    public <T, U> List<Predicate> createPredicatesFromStrings(
            Path<T> path, CriteriaBuilder criteriaBuilder, String[] criteriaList
    ) {
        List<Predicate> predicates = new ArrayList<>();
        Pattern pattern = Pattern.compile(SEARCH_SPEC_OPERATOR);
        for (String criteria : criteriaList) {
            Matcher matcher = pattern.matcher(criteria);
            if (matcher.find()) {
                SpecSearchCriteria specSearchCriteria = new SpecSearchCriteria(
                        matcher.group(1),
                        matcher.group(2),
                        matcher.group(3),
                        matcher.group(4),
                        matcher.group(5)
                );
                Predicate predicate = toPredicate(path, criteriaBuilder, specSearchCriteria);
                predicates.add(predicate);
            }
        }
        return predicates;
    }
}
