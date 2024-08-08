package com.revature.DAOs;

import com.revature.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Long> {

    /* Custom query method to find reimbursements by user ID */
    List<Reimbursement> findByUserUserId(Long userId);

    /* Custom query method to find reimbursements by status */
    List<Reimbursement> findByStatus(String status);
}
