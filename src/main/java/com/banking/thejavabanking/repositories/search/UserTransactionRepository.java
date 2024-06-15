package com.banking.thejavabanking.repositories.search;

import com.banking.thejavabanking.dto.respones.shared.PageResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.banking.thejavabanking.utils.AppConst.LIKE_FORMAT;
import static com.banking.thejavabanking.utils.AppConst.SORT_BY;

@Repository
public class UserTransactionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PageResponse<?> getAllTransactionCurrentUserWithBySort(
            int pageNo, int pageSize, String search, String sortBy
    ) {
        StringBuilder builder = new StringBuilder("SELECT u FROM UserTransaction u WHERE 1=1");

        // if you want to use DTO, you can use this query. If 1 field is null, it will return null
//        StringBuilder builder = new StringBuilder(
//                "SELECT new com.banking.thejavabanking.dto.respones.UserResponse(u.id, u.email, u.firstName, u.lastName, u.otherName, u.email) FROM User u WHERE 1=1"
//        );
        if (StringUtils.hasText(search)) {
            builder.append(" AND lower(u.transactionType) LIKE lower(:transactionType)");
            builder.append(" OR u.accountId = :accountId");
            builder.append(" OR u.amount = :amount");
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
            selectQuery.setParameter("transactionType", String.format(LIKE_FORMAT, search));
            selectQuery.setParameter("accountId", String.format(LIKE_FORMAT, search));
            selectQuery.setParameter("amount", String.format(LIKE_FORMAT, search));
        }
        selectQuery.setFirstResult(pageNo);
        selectQuery.setMaxResults(pageSize);
        List<?> users = selectQuery.getResultList();
        System.out.println(users);

        StringBuilder countBuilder = new StringBuilder("SELECT COUNT(*) FROM UserTransaction u");
        if (StringUtils.hasText(search)) {
            countBuilder.append(" WHERE lower(u.transactionType) LIKE lower(?1)");
            countBuilder.append(" OR u.accountId = :accountId");
            countBuilder.append(" OR u.amount = :amount");
        }
        Query countQuery = entityManager.createQuery(countBuilder.toString());
        if (StringUtils.hasText(search)) {
            countQuery.setParameter(1, String.format(LIKE_FORMAT, search));
            countQuery.setParameter(2, String.format(LIKE_FORMAT, search));
            countQuery.setParameter(3, String.format(LIKE_FORMAT, search));
        }
        Long totalElements = (Long) countQuery.getSingleResult();
        System.out.println(totalElements);

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
}
