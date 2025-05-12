package com.cts.demo.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cts.demo.exception.ClaimNotFoundException;
import com.cts.demo.model.Claim;
import com.cts.demo.service.ClaimService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/claim")
public class ClaimController {

    private static final Logger logger = LoggerFactory.getLogger(ClaimController.class);

    @Autowired
    private ClaimService claimService;

    /**
     * Endpoint to file a new claim.
     * Accepts a Claim object in the request body and returns a success message.
     * Uses HTTP POST method and the "/file" path.
     * @param claim The Claim object containing the details of the claim to be filed.
     * @return ResponseEntity containing a success message and HTTP status CREATED (201).
     */
    @PostMapping("/file")
    public ResponseEntity<String> fileClaim(@Valid @RequestBody Claim claim) {
        logger.info("Filing a new claim");
        String message = claimService.fileClaim(claim);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    /**
     * Endpoint to review a claim's validity based on its ID.
     * Accepts the claim ID as a path variable.
     * Uses HTTP PUT method and the "/review/validity/{claimId}" path.
     * @param claimId The ID of the claim to be reviewed.
     * @return ResponseEntity containing the updated Claim object and HTTP status OK (200).
     * @throws ClaimNotFoundException If the claim with the given ID is not found.
     */
    @PutMapping("/review/validity/{claimId}")
    public ResponseEntity<Claim> reviewClaimByValidity(@PathVariable long claimId) throws ClaimNotFoundException {
        logger.info("Reviewing claim based on validity period for ID: {}", claimId);
        Claim updatedClaim = claimService.reviewClaimByIdAndValidityPeriod(claimId);
        return ResponseEntity.ok(updatedClaim);
    }

    /**
     * Endpoint to review a claim based on its amount using its ID.
     * Accepts the claim ID as a path variable.
     * Uses HTTP PUT method and the "/review/amount/{claimId}" path.
     * @param claimId The ID of the claim to be reviewed.
     * @return ResponseEntity containing the updated Claim object and HTTP status OK (200).
     * @throws ClaimNotFoundException If the claim with the given ID is not found.
     */
    @PutMapping("/review/amount/{claimId}")
    public ResponseEntity<Claim> reviewClaimByAmount(@PathVariable long claimId) throws ClaimNotFoundException {
        logger.info("Reviewing claim based on amount for ID: {}", claimId);
        Claim updatedClaim = claimService.reviewClaimByIdAndAmount(claimId);
        return ResponseEntity.ok(updatedClaim);
    }

    /**
     * Endpoint to retrieve the status of a claim based on its ID.
     * Accepts the claim ID as a path variable.
     * Uses HTTP GET method and the "/status/{claimId}" path.
     * @param claimId The ID of the claim whose status is to be retrieved.
     * @return ResponseEntity containing the status of the claim and HTTP status OK (200).
     */
    @GetMapping("/status/{claimId}")
    public ResponseEntity<String> getClaimStatus(@PathVariable long claimId) {
        logger.info("Getting claim status for ID: {}", claimId);
        String status = claimService.claimStatus(claimId);
        return ResponseEntity.ok(status);
    }

    /**
     * Endpoint to retrieve a specific claim based on its ID.
     * Accepts the claim ID as a path variable.
     * Uses HTTP GET method and the "/{claimId}" path.
     * @param claimId The ID of the claim to be retrieved.
     * @return ResponseEntity containing the Claim object and HTTP status OK (200).
     * @throws ClaimNotFoundException If the claim with the given ID is not found.
     */
    @GetMapping("/{claimId}")
    public ResponseEntity<Claim> getClaimById(@PathVariable long claimId) throws ClaimNotFoundException {
        logger.info("Retrieving claim by ID: {}", claimId);
        Claim claim = claimService.getClaimById(claimId);
        return ResponseEntity.ok(claim);
    }

    /**
     * Endpoint to retrieve all claims.
     * Uses HTTP GET method and the "/all" path.
     * @return ResponseEntity containing a list of all Claim objects and HTTP status OK (200).
     */
    @GetMapping("/all")
    public ResponseEntity<List<Claim>> getAllClaims() {
        logger.info("Retrieving all claims");
        List<Claim> claims = claimService.getAllClaims();
        return ResponseEntity.ok(claims);
    }

    /**
     * Endpoint to delete a claim based on its ID.
     * Accepts the claim ID as a path variable.
     * Uses HTTP DELETE method and the "/{claimId}" path.
     * @param claimId The ID of the claim to be deleted.
     * @return ResponseEntity containing a success message and HTTP status OK (200).
     */
    @DeleteMapping("/{claimId}")
    public ResponseEntity<String> deleteClaimById(@PathVariable long claimId) throws ClaimNotFoundException {
        logger.info("Deleting claim with ID: {}", claimId);
        claimService.deleteClaimById(claimId); // No need to capture the message if you handle the exception
        return new ResponseEntity<>("Claim deleted successfully", HttpStatus.OK);
    }
}