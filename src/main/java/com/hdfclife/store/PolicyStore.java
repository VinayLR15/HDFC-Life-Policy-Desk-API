package com.hdfclife.store;

import com.hdfclife.model.Claim;
import com.hdfclife.model.Policy;

import java.util.List;
import java.util.Optional;

public interface PolicyStore {

    long count();

    void add(Policy policy);

    List<Policy> findAll();

    Optional<Policy> findByPolicyNo(String policyNo);

    List<Policy> findByStatus(String status);

    List<Policy> findByType(String type);

    Policy update(String policyNo, Policy policy);

    boolean delete(String policyNo);

    void addClaim(Claim claim);

    List<Claim> findClaimsByPolicyNo(String policyNo);

    Optional<Claim> findClaimByNo(String claimNo);
}