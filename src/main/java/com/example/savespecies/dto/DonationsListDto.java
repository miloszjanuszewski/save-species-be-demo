package com.example.savespecies.dto;

import java.util.List;

public record DonationsListDto (
        List<DonationDto> donationDtos
) {}
