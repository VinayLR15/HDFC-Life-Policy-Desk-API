package com.hdfclife.service;

import com.hdfclife.exception.DuplicatePolicyException;
import com.hdfclife.exception.PolicyNotFoundException;
import com.hdfclife.model.Policy;
import com.hdfclife.store.PolicyStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {

    private final PolicyStore policyStore;

    public PolicyService(PolicyStore policyStore) {
        this.policyStore = policyStore;
    }

    public List<Policy> getAllPolicies() {
        return policyStore.findAll();
    }

    public Policy getPolicy(String policyNo) {

        return policyStore.findByPolicyNo(policyNo)
                .orElseThrow(() ->
                        new PolicyNotFoundException(
                                "Policy not found: " + policyNo
                        ));
    }

    public List<Policy> getPolicies(
            String status,
            String type) {

        if (status == null && type == null) {
            return policyStore.findAll();
        }

        if (status != null && type != null) {
            return policyStore.findAll()
                    .stream()
                    .filter(policy ->
                            policy.getStatus().equals(status)
                                    && policy.getType().equals(type))
                    .toList();
        }

        if (status != null) {
            return policyStore.findByStatus(status);
        }

        return policyStore.findByType(type);
    }

    public Policy createPolicy(Policy policy) {

        if (policyStore.findByPolicyNo(policy.getPolicyNo()).isPresent()) {
            throw new DuplicatePolicyException(
                    "Policy already exists: " + policy.getPolicyNo()
            );
        }

        policyStore.add(policy);

        return policy;
    }

    public Policy updatePolicy(
            String policyNo,
            Policy policy) {

        if (policyStore.findByPolicyNo(policyNo).isEmpty()) {
            throw new PolicyNotFoundException(
                    "Policy not found: " + policyNo
            );
        }

        policy.setPolicyNo(policyNo);

        return policyStore.update(policyNo, policy);
    }

    public void deletePolicy(String policyNo) {

        boolean deleted = policyStore.delete(policyNo);

        if (!deleted) {
            throw new PolicyNotFoundException(
                    "Policy not found: " + policyNo
            );
        }
    }

    public long getActivePolicyCount() {

        return policyStore.findByStatus("Active")
                .size();
    }

    public long getTermPolicyCount() {

        return policyStore.findByType("TERM")
                .size();
    }

    public long getUniqueCustomerCount() {

        return policyStore.findAll()
                .stream()
                .map(Policy::getCustomer)
                .distinct()
                .count();
    }

    public PolicyStore getPolicyStore() {
        return policyStore;
    }
}