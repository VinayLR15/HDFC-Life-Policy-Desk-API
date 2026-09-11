package com.hdfclife.web;

import com.hdfclife.model.Claim;
import com.hdfclife.model.CreateClaimRequest;
import com.hdfclife.model.ErrorResponse;
import com.hdfclife.service.ClaimService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/claims")
@Tag(
        name = "Claims",
        description = "HDFC Life claim management APIs"
)
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping
    @Operation(
            summary = "File a claim",
            description = "Creates and submits a claim for an existing policy"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Claim submitted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Claim.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid claim",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
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
    public ResponseEntity<Claim> createClaim(
            @RequestBody CreateClaimRequest request) {

        Claim created = claimService.createClaim(
                request.getPolicyNo(),
                request.getClaimAmount(),
                request.getUrgency()
        );

        URI location = URI.create(
                "/api/claims/" + created.getClaimNo()
        );

        return ResponseEntity
                .created(location)
                .body(created);
    }

    @GetMapping("/{claimNo}")
    @Operation(
            summary = "Get claim by claim number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Claim found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Claim.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Claim not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<Claim> getClaim(
            @PathVariable String claimNo) {

        return ResponseEntity.ok(
                claimService.getClaim(claimNo)
        );
    }
}