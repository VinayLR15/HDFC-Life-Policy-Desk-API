package com.hdfclife.model;

public class Claim {

    private String claimNo;
    private String policyNo;
    private int claimAmount;
    private Urgency urgency;
    private String status;

    public Claim() {
    }

    public Claim(
            String claimNo,
            String policyNo,
            int claimAmount,
            Urgency urgency,
            String status) {

        this.claimNo = claimNo;
        this.policyNo = policyNo;
        this.claimAmount = claimAmount;
        this.urgency = urgency;
        this.status = status;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public void setClaimNo(String claimNo) {
        this.claimNo = claimNo;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public int getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(int claimAmount) {
        this.claimAmount = claimAmount;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}