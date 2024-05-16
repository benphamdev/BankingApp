package com.banking.thejavabanking.repositories.search;

import com.banking.thejavabanking.dto.respones.PageResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.banking.thejavabanking.utils.AppConst.LIKE_FORMAT;
import static com.banking.thejavabanking.utils.AppConst.SORT_BY;

@Repository
@Slf4j
public class SearchPostRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public PageResponse<?> getAllPostsWithPagingAndSorting(
            int pageNo, int pageSize, String search, String sortBy
    ) {
        StringBuilder stringBuilder = new StringBuilder("SELECT p FROM Post p WHERE 1=1");

        if (StringUtils.hasLength(search)) {
            stringBuilder.append(" AND lower(p.name) LIKE lower(:name)");
            stringBuilder.append(" OR lower(p.content) LIKE lower(:content)");
        }

        if (StringUtils.hasLength(sortBy)) {
            Pattern pattern = Pattern.compile(SORT_BY);
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find())
                stringBuilder.append(String.format(" ORDER BY p.%s %s", matcher.group(1), matcher.group(3)));

        }

        Query query = entityManager.createQuery(stringBuilder.toString());

        if (StringUtils.hasLength(search)) {
            query.setParameter("name", String.format(LIKE_FORMAT, search));
            query.setParameter("content", String.format(LIKE_FORMAT, search));
        }
        query.setFirstResult(pageNo);
        query.setMaxResults(pageSize);
        List<?> posts = query.getResultList();

        StringBuilder countBuilder = new StringBuilder("SELECT COUNT(*) FROM Post p");
        if (StringUtils.hasLength(search)) {
            countBuilder.append(" WHERE lower(p.name) LIKE lower(?1)");
            countBuilder.append(" OR lower(p.content) LIKE lower(?2)");
        }

        Query countQuery = entityManager.createQuery(countBuilder.toString());
        if (StringUtils.hasLength(search)) {
            countQuery.setParameter(1, String.format(LIKE_FORMAT, search));
            countQuery.setParameter(2, String.format(LIKE_FORMAT, search));
            countQuery.getSingleResult();
        }

        Long totalElements = (Long) countQuery.getSingleResult();
        log.info("Total elements: {}", totalElements);

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<?> page = new PageImpl<>(posts, pageable, totalElements);

        return PageResponse.builder()
                           .page(pageNo)
                           .size(pageSize)
                           .total(page.getTotalPages())
                           .items(posts)
                           .build();
    }
}
