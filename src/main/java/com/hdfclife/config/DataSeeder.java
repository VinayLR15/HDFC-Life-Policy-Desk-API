package com.hdfclife.config;

import com.hdfclife.model.Policy;
import com.hdfclife.service.PolicyService;
import com.hdfclife.store.InMemoryPolicyStore;
import com.hdfclife.store.PolicyStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final PolicyStore policyStore;
    private final PolicyService policyService;
    private final HdfcProperties hdfcProperties;
    private final Environment environment;

    public DataSeeder(
            PolicyStore policyStore,
            PolicyService policyService,
            HdfcProperties hdfcProperties,
            Environment environment) {
        this.policyStore = policyStore;
        this.policyService = policyService;
        this.hdfcProperties = hdfcProperties;
        this.environment = environment;
    }

    @Override
    public void run(String... args) {

        if (policyStore.count() == 0) {
            policyStore.add(new Policy(
                    "HDFC-LIFE-1001",
                    "Anita Sharma",
                    "TERM",
                    18500,
                    "Active"
            ));

            policyStore.add(new Policy(
                    "HDFC-LIFE-1002",
                    "Rahul Mehta",
                    "ULIP",
                    42000,
                    "Active"
            ));

            policyStore.add(new Policy(
                    "HDFC-LIFE-1003",
                    "Priya Nair",
                    "ENDOWMENT",
                    27000,
                    "Lapsed"
            ));

            policyStore.add(new Policy(
                    "HDFC-LIFE-1004",
                    "Vikram Singh",
                    "TERM",
                    15200,
                    "Active"
            ));

            policyStore.add(new Policy(
                    "HDFC-LIFE-1005",
                    "Sneha Patel",
                    "ULIP",
                    36000,
                    "Active"
            ));

            policyStore.add(new Policy(
                    "HDFC-LIFE-1006",
                    "Anita Sharma",
                    "ENDOWMENT",
                    22000,
                    "Pending"
            ));
        }

        List<Policy> policies = policyStore.findAll();

        long activeCount = policyService.getPolicies("Active", null).size();
        long termCount = policyService.getPolicies(null, "TERM").size();

        long uniqueCustomers = policies.stream()
                .map(Policy::getCustomer)
                .distinct()
                .count();

        System.out.println("Active profile → " +
                String.join(",", environment.getActiveProfiles()));

        System.out.println("Company name → " +
                hdfcProperties.getCompanyName());

        System.out.println("Max claim amount → " +
                hdfcProperties.getMaxClaimAmount());

        System.out.println("Seeded policy count → " +
                policies.size());

        System.out.println("Lookup HDFC-LIFE-1004 customer → " +
                policyService.getPolicy("HDFC-LIFE-1004").getCustomer());

        System.out.println("Active policy count via PolicyService → " +
                activeCount);

        System.out.println("TERM policy count via PolicyService → " +
                termCount);

        System.out.println("Unique customer count → " +
                uniqueCustomers);

        System.out.println("Simple class name of injected PolicyStore → " +
                InMemoryPolicyStore.class.getSimpleName());
    }
}