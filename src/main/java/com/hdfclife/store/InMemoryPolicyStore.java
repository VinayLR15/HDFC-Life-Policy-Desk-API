package com.hdfclife.store;

import com.hdfclife.model.Claim;
import com.hdfclife.model.Policy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryPolicyStore implements PolicyStore {

    private final Map<String, Policy> policies = new LinkedHashMap<>();
    private final Map<String, Claim> claims = new LinkedHashMap<>();

    @Override
    public synchronized long count() {
        return policies.size();
    }

    @Override
    public synchronized void add(Policy policy) {
        policies.put(policy.getPolicyNo(), policy);
    }

    @Override
    public synchronized List<Policy> findAll() {
        return new ArrayList<>(policies.values());
    }

    @Override
    public synchronized Optional<Policy> findByPolicyNo(String policyNo) {
        return Optional.ofNullable(policies.get(policyNo));
    }

    @Override
    public synchronized List<Policy> findByStatus(String status) {
        return policies.values()
                .stream()
                .filter(policy -> policy.getStatus().equals(status))
                .toList();
    }

    @Override
    public synchronized List<Policy> findByType(String type) {
        return policies.values()
                .stream()
                .filter(policy -> policy.getType().equals(type))
                .toList();
    }

    @Override
    public synchronized Policy update(String policyNo, Policy policy) {

        Policy existing = policies.get(policyNo);

        if (existing == null) {
            return null;
        }

        existing.setCustomer(policy.getCustomer());
        existing.setType(policy.getType());
        existing.setBasePremium(policy.getBasePremium());
        existing.setStatus(policy.getStatus());

        return existing;
    }

    @Override
    public synchronized boolean delete(String policyNo) {
        return policies.remove(policyNo) != null;
    }

    @Override
    public synchronized void addClaim(Claim claim) {
        claims.put(claim.getClaimNo(), claim);
    }

    @Override
    public synchronized List<Claim> findClaimsByPolicyNo(String policyNo) {
        return claims.values()
                .stream()
                .filter(claim -> claim.getPolicyNo().equals(policyNo))
                .toList();
    }

    @Override
    public synchronized Optional<Claim> findClaimByNo(String claimNo) {
        return Optional.ofNullable(claims.get(claimNo));
    }
}