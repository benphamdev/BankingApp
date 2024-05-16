package com.banking.thejavabanking.repositories;

import com.banking.thejavabanking.models.entity.Saving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Integer> {
    @Modifying
    @Transactional
    // update amount
    @Query(
            value = "UPDATE tbl_saving SET refund_amount = refund_amount + ?2 WHERE id = ?1",
            nativeQuery = true
    )
    void updateAmount(int id, BigDecimal amount);

    @Modifying
    @Transactional
    // update status refund
    @Query(
            value = "UPDATE tbl_saving SET status_refund = ?2 WHERE id = ?1",
            nativeQuery = true
    )
    void updateStatusRefund(int id, boolean statusRefund);

    // find by user id

    Optional<Saving> findByUserId(Integer userId);

    List<Saving> findSavingsByStatusRefundIsFalse();
}
