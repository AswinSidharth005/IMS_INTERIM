package com.cts.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.demo.dto.Policy;
import com.cts.demo.exception.ClaimNotFoundException;
import com.cts.demo.feignclient.NotificationClient;
import com.cts.demo.feignclient.PolicyClient;
import com.cts.demo.model.Claim;
import com.cts.demo.repository.ClaimRepository;
import com.cts.demo.service.ClaimServiceImpl;

public class ClaimApplicationTests {

    @Mock
    private ClaimRepository repository;

    @Mock
    private PolicyClient policyClient;

    @Mock
    private NotificationClient notificationClient;

    @InjectMocks
    private ClaimServiceImpl claimService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFileClaim() {
        Claim claim = new Claim();
        when(repository.save(claim)).thenReturn(claim);

        String result = claimService.fileClaim(claim);
        assertEquals("New claim request has been filed successfully", result);
        verify(repository, times(1)).save(claim);
    }





    @Test
    public void testClaimStatus() {
        long claimId = 1L;
        Claim claim = new Claim();
        claim.setClaimStatus("APPROVED");

        when(repository.findById(claimId)).thenReturn(Optional.of(claim));

        String result = claimService.claimStatus(claimId);
        assertEquals("APPROVED", result);
        verify(notificationClient, times(1)).notify(anyString(), eq(claim.getCustomerId()), eq(claim.getClaimId()));
    }

    @Test
    public void testClaimStatusNotFound() {
        long claimId = 1L;
        when(repository.findById(claimId)).thenReturn(Optional.empty());

        String result = claimService.claimStatus(claimId);
        assertEquals("Claim not found.", result);
    }




    @Test
    public void testGetClaimById() throws ClaimNotFoundException {
        long claimId = 1L;
        Claim claim = new Claim();

        when(repository.findById(claimId)).thenReturn(Optional.of(claim));

        Claim result = claimService.getClaimById(claimId);
        assertEquals(claim, result);
    }

    @Test
    public void testGetClaimByIdNotFound() {
        long claimId = 1L;
        when(repository.findById(claimId)).thenReturn(Optional.empty());

        assertThrows(ClaimNotFoundException.class, () -> {
            claimService.getClaimById(claimId);
        });
    }

    @Test
    public void testGetAllClaims() {
        List<Claim> claims = new ArrayList<>();
        when(repository.findAll()).thenReturn(claims);

        List<Claim> result = claimService.getAllClaims();
        assertEquals(claims, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testDeleteClaimById() throws ClaimNotFoundException {
        long claimId = 1L;
        when(repository.existsById(claimId)).thenReturn(true);

        String result = claimService.deleteClaimById(claimId);
        assertEquals("Claim deleted successfully", result);
        verify(repository, times(1)).deleteById(claimId);
    }

    @Test
    public void testDeleteClaimByIdNotFound() {
        long claimId = 1L;
        when(repository.existsById(claimId)).thenReturn(false);

        assertThrows(ClaimNotFoundException.class, () -> {
            claimService.deleteClaimById(claimId);
        });
        verify(repository, never()).deleteById(claimId);
    }
}
