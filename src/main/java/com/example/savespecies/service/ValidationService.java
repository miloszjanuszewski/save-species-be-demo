package com.example.savespecies.service;

import com.example.savespecies.exception.DonationNotFoundException;
import com.example.savespecies.exception.SpeciesNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ValidationService {

    private final DonationsService donationsService;
    private final SpeciesService speciesService;

    public void validateDonationExists(String donationId) {
        if (donationsService.getDonationDetails(donationId).isEmpty()) {
            throw new DonationNotFoundException(donationId);
        }
    }

    public void validateSpeciesExists(String speciesName) {
        if (speciesService.getSpecies(speciesName).isEmpty()) {
            throw new SpeciesNotFoundException(speciesName);
        }
    }
}
