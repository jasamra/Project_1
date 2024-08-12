package com.revature.DAOs;

import com.revature.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Long> {

    // Find reimbursements by user ID
    List<Reimbursement> findByUserUserId(Long userId);

    // Find reimbursements by status
    List<Reimbursement> findByStatus(String status);
    //List<Reimbursement> findByUserIdAndStatus(Long userId, String status);
    List<Reimbursement> findByUser_UserIdAndStatus(Long userId, String status);
    Optional<Reimbursement> findByReimbId(Long reimbId);   // Method to find a reimbursement by ID



}
