package com.cts.demo.service;

import java.util.List;
import com.cts.demo.exception.ClaimNotFoundException;
import com.cts.demo.model.Claim;

public interface ClaimService {

	String fileClaim(Claim claim); // Files a new claim.

	Claim reviewClaimByIdAndAmount(long claimId) throws ClaimNotFoundException; // Reviews claim by ID and amount.

	String claimStatus(long claimId); // Gets the status of a claim.

	Claim reviewClaimByIdAndValidityPeriod(long claimId) throws ClaimNotFoundException; // Reviews claim by ID and validity.

	Claim getClaimById(long claimId) throws ClaimNotFoundException; // Gets a claim by ID.

	List<Claim> getAllClaims(); // Gets all claims.

	String deleteClaimById(long claimId); // Deletes a claim by ID.
}