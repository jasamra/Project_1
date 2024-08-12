package com.revature.services;

import com.revature.DAOs.ReimbursementDAO;
import com.revature.models.Reimbursement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReimbursementService {

    private final ReimbursementDAO reimbursementDAO;

    @Autowired
    public ReimbursementService(ReimbursementDAO reimbursementDAO) {
        this.reimbursementDAO = reimbursementDAO;
    }

    // Create a new reimbursement
    public Reimbursement createReimbursement(Reimbursement newReimbursement) {
        return reimbursementDAO.save(newReimbursement);
    }

    // Get all reimbursements
    public List<Reimbursement> getAllReimbursements() {
        return reimbursementDAO.findAll();
    }

    // Get reimbursements by user ID
    public List<Reimbursement> getReimbursementsByUserId(Long userId) {
        return reimbursementDAO.findByUserUserId(userId);
    }

    // Get reimbursements by status
    public List<Reimbursement> getReimbursementsByStatus(String status) {
        return reimbursementDAO.findByStatus(status);
    }

    public List<Reimbursement> getReimbursementsByUserIdAndStatus(Long userId, String status) {
        return reimbursementDAO.findByUser_UserIdAndStatus(userId, status);
    }

    public List<Reimbursement> findByUser_UserIdAndStatus(Long userId, String status) {
        return reimbursementDAO.findByUser_UserIdAndStatus(userId, status);
    }

    // Update a reimbursement's status
    public Reimbursement updateReimbursementStatus(Long reimbId, String status) {
        Optional<Reimbursement> existingReimbursement = reimbursementDAO.findById(reimbId);
        if (existingReimbursement.isPresent()) {
            Reimbursement reimbursement = existingReimbursement.get();
            reimbursement.setStatus(status);
            return reimbursementDAO.save(reimbursement);
        } else {
            throw new RuntimeException("Reimbursement not found.");
        }
    }



    // Delete a reimbursement
    public void deleteReimbursement(Long reimbId) {
        if (reimbursementDAO.existsById(reimbId)) {
            reimbursementDAO.deleteById(reimbId);
        } else {
            throw new RuntimeException("Reimbursement not found.");
        }
    }


}
