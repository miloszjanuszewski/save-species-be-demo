package com.example.savespecies.dto;

public record DonationDetailsDto(
        String donationId,
        String userId,
        String species,
        String amount
) {}
