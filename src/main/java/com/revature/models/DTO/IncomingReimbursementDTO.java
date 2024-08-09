package com.revature.models.DTO;

public class IncomingReimbursementDTO {

    private String description;
    private Double amount;
    private String status;
    private Long userId;

    // Constructors
    public IncomingReimbursementDTO() {
    }

    public IncomingReimbursementDTO(String description, Double amount, String status, Long userId) {
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.userId = userId;
    }

    // Getters and Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "IncomingReimbursementDTO{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                '}';
    }
}
