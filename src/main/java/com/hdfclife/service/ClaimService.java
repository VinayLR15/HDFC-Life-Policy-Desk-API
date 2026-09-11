package com.hdfclife.service;

import com.hdfclife.config.HdfcProperties;
import com.hdfclife.exception.ClaimNotFoundException;
import com.hdfclife.exception.InvalidClaimException;
import com.hdfclife.exception.PolicyNotFoundException;
import com.hdfclife.model.Claim;
import com.hdfclife.model.Urgency;
import com.hdfclife.store.PolicyStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimService {

    private final PolicyStore policyStore;
    private final HdfcProperties hdfcProperties;

    private int nextClaimNumber = 1;

    public ClaimService(
            PolicyStore policyStore,
            HdfcProperties hdfcProperties) {

        this.policyStore = policyStore;
        this.hdfcProperties = hdfcProperties;
    }

    public synchronized Claim createClaim(
            String policyNo,
            int claimAmount,
            Urgency urgency) {

        if (claimAmount <= 0) {
            throw new InvalidClaimException(
                    "Claim amount must be greater than 0"
            );
        }

        if (claimAmount > hdfcProperties.getMaxClaimAmount()) {
            throw new InvalidClaimException(
                    "Claim amount exceeds maximum allowed amount of "
                            + hdfcProperties.getMaxClaimAmount()
            );
        }

        if (policyStore.findByPolicyNo(policyNo).isEmpty()) {
            throw new PolicyNotFoundException(
                    "Policy not found: " + policyNo
            );
        }

        if (urgency == null) {
            throw new InvalidClaimException(
                    "Urgency is required"
            );
        }

        String claimNo = String.format(
                "CLM-%02d",
                nextClaimNumber++
        );

        Claim claim = new Claim(
                claimNo,
                policyNo,
                claimAmount,
                urgency,
                "SUBMITTED"
        );

        policyStore.addClaim(claim);

        return claim;
    }

    public Claim getClaim(String claimNo) {

        return policyStore.findClaimByNo(claimNo)
                .orElseThrow(() ->
                        new ClaimNotFoundException(
                                "Claim not found: " + claimNo
                        ));
    }

    public List<Claim> getClaimsForPolicy(String policyNo) {

        if (policyStore.findByPolicyNo(policyNo).isEmpty()) {
            throw new PolicyNotFoundException(
                    "Policy not found: " + policyNo
            );
        }

        return policyStore.findClaimsByPolicyNo(policyNo);
    }
}