package com.example.savespecies.dto;

import java.util.List;

public record DonationDetailsListDto(
        List<DonationDetailsDto> donationDetailsDtoList
) {}
