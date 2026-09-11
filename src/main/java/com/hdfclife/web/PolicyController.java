package com.hdfclife.web;

import com.hdfclife.model.Claim;
import com.hdfclife.model.ErrorResponse;
import com.hdfclife.model.Policy;
import com.hdfclife.service.ClaimService;
import com.hdfclife.service.PolicyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/policies")
@Tag(
        name = "Policies",
        description = "HDFC Life policy management APIs"
)
public class PolicyController {

    private final PolicyService policyService;
    private final ClaimService claimService;

    public PolicyController(
            PolicyService policyService,
            ClaimService claimService) {

        this.policyService = policyService;
        this.claimService = claimService;
    }

    @GetMapping
    @Operation(
            summary = "Get policies",
            description = "Returns all policies or filters by status and/or type"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Policies returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Policy.class)
                            )
                    )
            )
    })
    public ResponseEntity<List<Policy>> getPolicies(
            @Parameter(
                    name = "status",
                    description = "Policy status",
                    in = ParameterIn.QUERY,
                    required = false
            )
            @RequestParam(required = false) String status,

            @Parameter(
                    name = "type",
                    description = "Policy type",
                    in = ParameterIn.QUERY,
                    required = false
            )
            @RequestParam(required = false) String type) {

        return ResponseEntity.ok(
                policyService.getPolicies(status, type)
        );
    }

    @GetMapping("/{policyNo}")
    @Operation(
            summary = "Get policy by policy number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Policy found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Policy.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Policy not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Policy> getPolicy(
            @PathVariable String policyNo) {

        return ResponseEntity.ok(
                policyService.getPolicy(policyNo)
        );
    }

    @PostMapping
    @Operation(
            summary = "Create a policy"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Policy created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Policy.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Duplicate policy number",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Policy> createPolicy(
            @RequestBody Policy policy) {

        Policy created = policyService.createPolicy(policy);

        URI location = URI.create(
                "/api/policies/" + created.getPolicyNo()
        );

        return ResponseEntity
                .created(location)
                .body(created);
    }

    @PutMapping("/{policyNo}")
    @Operation(
            summary = "Replace an existing policy"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Policy updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Policy.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Policy not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Policy> updatePolicy(
            @PathVariable String policyNo,
            @RequestBody Policy policy) {

        return ResponseEntity.ok(
                policyService.updatePolicy(policyNo, policy)
        );
    }

    @DeleteMapping("/{policyNo}")
    @Operation(
            summary = "Delete a policy"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Policy deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Policy not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Void> deletePolicy(
            @PathVariable String policyNo) {

        policyService.deletePolicy(policyNo);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{policyNo}/claims")
    @Operation(
            summary = "Get claims for a policy",
            description = "Returns claims for the policy in oldest-first order"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Claims returned",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Claim.class)
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Policy not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<List<Claim>> getPolicyClaims(
            @PathVariable String policyNo) {

        return ResponseEntity.ok(
                claimService.getClaimsForPolicy(policyNo)
        );
    }
}