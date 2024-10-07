package com.example.savespecies.service;

import com.example.savespecies.dao.DonationsDao;
import com.example.savespecies.dto.DonationDetailsDto;
import com.example.savespecies.dto.DonationDetailsListDto;
import com.example.savespecies.dto.DonationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DonationsService {

    private final DonationsDao donationsDao;

    public void recordDonation(DonationDto donationDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        donationsDao.insertDonation(donationDto, authentication.getName());
    }

    public Optional<DonationDetailsDto> getDonationDetails(String donationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return donationsDao.getDonationDetails(userId, donationId);
    }

    public DonationDetailsListDto getDonationDetailsList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return new DonationDetailsListDto(donationsDao.getDonationDetailsListForUser(userId));
    }

    public void removeDonation(String donationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        donationsDao.deleteDonation(userId, donationId);
    }
}
