package com.hdfclife.model;

public class Policy {

    private String policyNo;
    private String customer;
    private String type;
    private int basePremium;
    private String status;

    public Policy() {
    }

    public Policy(
            String policyNo,
            String customer,
            String type,
            int basePremium,
            String status) {

        this.policyNo = policyNo;
        this.customer = customer;
        this.type = type;
        this.basePremium = basePremium;
        this.status = status;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBasePremium() {
        return basePremium;
    }

    public void setBasePremium(int basePremium) {
        this.basePremium = basePremium;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}