package com.cts.demo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.cts.demo.dto.Policy;
import com.cts.demo.exception.ClaimNotFoundException;
import com.cts.demo.feignclient.NotificationClient;
import com.cts.demo.feignclient.PolicyClient;
import com.cts.demo.model.Claim;
import com.cts.demo.repository.ClaimRepository;
import com.cts.demo.service.ClaimServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
class ClaimApplicationTests {
   @Mock
   ClaimRepository repository;
   @InjectMocks
   ClaimServiceImpl service;
   @Mock
   PolicyClient policyClient;
   @Mock
   NotificationClient notificationClient;
   @Test
   void saveTest() {
       Claim claim = new Claim(1, 2, 4, 70000, "IN-REVIEW"); // Removed agentId
       when(repository.save(claim)).thenReturn(claim);
       String response = service.fileClaim(claim);
       assertEquals("New claim request has been filed successfully", response);
   }
   @Test
   void reviewClaimByIdAndAmount_Approved() throws ClaimNotFoundException {
       Claim claim = new Claim(1, 101L, 4, 50000, "IN-REVIEW"); // Removed agentId
       Policy policy = new Policy(4, "Health", 100000, "hospital expenses", LocalDate.of(2025, 6, 21));
       when(repository.findById(1L)).thenReturn(Optional.of(claim));
       when(policyClient.retrievePolicy(4)).thenReturn(policy);
       when(repository.updateClaimStatus("APPROVED", 1L)).thenReturn(1);
       Claim updatedClaim = new Claim(1, 101, 4, 50000, "APPROVED"); // Removed agentId
       when(repository.findById(1L)).thenReturn(Optional.of(updatedClaim));
       Claim result = service.reviewClaimByIdAndAmount(1L);
       assertEquals("APPROVED", result.getClaimStatus());
   }
   @Test
   void reviewClaimByIdAndValidityPeriod() throws ClaimNotFoundException {
       Claim claim = new Claim(1, 101L, 4, 50000, "IN-REVIEW");
       Policy policy = new Policy(4, "Health", 100000, "hospital expenses", LocalDate.of(2025, 6, 21));
       when(repository.findById(1L)).thenReturn(Optional.of(claim));
       when(policyClient.retrievePolicy(4)).thenReturn(policy);
       when(repository.updateClaimStatus("IN-REVIEW", 1L)).thenReturn(1);
       Claim updatedClaim = new Claim(1, 101, 4, 50000, "IN-REVIEW");
       when(repository.findById(1L)).thenReturn(Optional.of(updatedClaim));
       Claim result = service.reviewClaimByIdAndValidityPeriod(1L);
       assertEquals("IN-REVIEW", result.getClaimStatus());
   }
   @Test
   void getClaimById() throws ClaimNotFoundException {
       Claim claim = new Claim(1, 101L, 4, 50000, "IN-REVIEW");
       when(repository.findById(1L)).thenReturn(Optional.of(claim));
       Claim result = service.getClaimById(1L);
       assertEquals(claim, result);
   }
   @Test
   void getAllClaims() {
       List<Claim> claims = Arrays.asList(
               new Claim(1, 101L, 4, 50000, "IN-REVIEW"),
               new Claim(2, 102L, 5, 50000, "IN-REVIEW"));
       when(repository.findAll()).thenReturn(claims);
       List<Claim> result = service.getAllClaims();
       assertEquals(claims, result);
   }
   @Test
   void deleteClaimById_ExistingClaim() throws ClaimNotFoundException {
       long claimId = 1L;

       // Mock the behavior of existsById to return true (claim exists)
       when(repository.existsById(claimId)).thenReturn(true);

       // Mock the behavior of deleteById to do nothing
       doNothing().when(repository).deleteById(claimId);

       // Call the service method
       String response = service.deleteClaimById(claimId);

       // Assert that the response is as expected
       assertEquals("Claim deleted successfully", response);

       // Verify that deleteById was called exactly once
       verify(repository, times(1)).deleteById(claimId);
       verify(repository, times(1)).existsById(claimId);
   }

   @Test
   void deleteClaimById_NonExistingClaim() {
       long claimId = 2L;

       // Mock the behavior of existsById to return false (claim does not exist)
       when(repository.existsById(claimId)).thenReturn(false);

       // Call the service method and assert that it throws ClaimNotFoundException
       assertThrows(ClaimNotFoundException.class, () -> service.deleteClaimById(claimId));

       // Verify that existsById was called exactly once
       verify(repository, times(1)).existsById(claimId);

       // Verify that deleteById was never called
       verify(repository, never()).deleteById(claimId);
   }

}