package com.cts.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cts.demo.dto.Policy;
import com.cts.demo.exception.ClaimNotFoundException;
import com.cts.demo.feignclient.NotificationClient;
import com.cts.demo.feignclient.PolicyClient;
import com.cts.demo.model.Claim;
import com.cts.demo.repository.ClaimRepository;

@Service("claimService")
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimRepository repository; // Handles claim data access
    @Autowired
    private PolicyClient policyClient; // Interacts with the Policy service
    @Autowired
    private NotificationClient notificationClient; // Sends notifications
    private static final Logger log = LoggerFactory.getLogger(ClaimServiceImpl.class);

    @Override
    public String fileClaim(Claim claim) {
        log.info("Filing a new claim...");
        Claim saved = repository.save(claim); // Saves the new claim
        //Ternary Operators
        return (saved != null) ? "New claim request has been filed successfully" : "Failed to file claim.";
    }

    @Override
    public Claim reviewClaimByIdAndAmount(long claimId) throws ClaimNotFoundException {
        log.info("Reviewing claim by amount for ID: {}", claimId);
        Claim claim = repository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("No claim found with ID: " + claimId)); // Get claim
        Policy policy = policyClient.retrievePolicy(claim.getPolicyId()); // Get associated policy
        double claimAmt = claim.getClaimAmount();
        double maxAmt = policy.getPremiumAmount();
        if (claimAmt <= maxAmt) {
            repository.updateClaimStatus("APPROVED", claimId); // Approve claim
            notificationClient.sendNotification("Your Policy validity period is less than 10 days", claimId, policy.getPolicyId()); // Send policy expiry notification
            notificationClient.notify("Claim status changed to APPROVED", claim.getCustomerId(), policy.getPolicyId()); // Notify claim approval
        } else {
            repository.updateClaimStatus("REJECTED", claimId); // Reject claim
            notificationClient.notify("Claim status changed to REJECTED. Please verify.", claim.getCustomerId(), policy.getPolicyId()); // Notify claim rejection
        }
        return repository.findById(claimId).get(); // Return updated claim
    }

    @Override
    public String claimStatus(long claimId) {
        log.info("Retrieving claim status for ID: {}", claimId);
        Optional<Claim> optional = repository.findById(claimId);
        if (optional.isPresent()) {
            Claim claim = optional.get();
            String status = claim.getClaimStatus();
            notificationClient.notify("Your claim status is: " + status, claim.getCustomerId(), claim.getClaimId()); // Notify claim status
            return status; // Return claim status
        } else {
            return "Claim not found.";
        }
    }

    @Override
    public Claim reviewClaimByIdAndValidityPeriod(long claimId) throws ClaimNotFoundException {
        log.info("Reviewing claim by policy validity for ID: {}", claimId);
        Claim claim = repository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("No claim found with ID: " + claimId)); // Get claim
        Policy policy = policyClient.retrievePolicy(claim.getPolicyId()); // Get associated policy
        LocalDate now = LocalDate.now();
        LocalDate validity = policy.getValidityPeriod();
        if (!validity.isBefore(now)) {
            repository.updateClaimStatus("IN-REVIEW", claimId); // Set claim to in-review
            notificationClient.notify("Claim status changed to IN-REVIEW", claim.getCustomerId(), policy.getPolicyId()); // Notify in-review status
        } else {
            repository.updateClaimStatus("REJECTED", claimId); // Reject claim
            notificationClient.notify("Claim REJECTED: Policy validity expired", claim.getCustomerId(), policy.getPolicyId()); // Notify rejection due to expiry
        }
        return repository.findById(claimId).get(); // Return updated claim
    }

    @Override
    public Claim getClaimById(long claimId) throws ClaimNotFoundException {
        log.info("Fetching claim by ID: {}", claimId);
        return repository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Enter a valid Claim ID: " + claimId)); // Get claim by ID
    }

    @Override
    public List<Claim> getAllClaims() {
        log.info("Fetching all claims...");
        return repository.findAll(); // Get all claims
    }

    @Override
    public String deleteClaimById(long claimId) throws ClaimNotFoundException {
        log.info("Deleting claim with ID: {}", claimId);
        if (!repository.existsById(claimId)) {
            throw new ClaimNotFoundException("Claim not found with ID: " + claimId);
        }
        repository.deleteById(claimId); // Delete claim by ID
        return "Claim deleted successfully";
    }}
