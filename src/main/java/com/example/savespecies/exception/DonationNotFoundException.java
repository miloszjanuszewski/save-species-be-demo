package com.example.savespecies.exception;

public class DonationNotFoundException extends RuntimeException{
    public DonationNotFoundException(String donationId) {
        super(String.format("Donation donationId=%s does not exist", donationId));
    }
}
