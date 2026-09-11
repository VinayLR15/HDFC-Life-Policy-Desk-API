package com.hdfclife.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "hdfc")
@Validated
public class HdfcProperties {

    @NotBlank
    private String companyName;

    @Min(1)
    private int maxClaimAmount;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getMaxClaimAmount() {
        return maxClaimAmount;
    }

    public void setMaxClaimAmount(int maxClaimAmount) {
        this.maxClaimAmount = maxClaimAmount;
    }
}