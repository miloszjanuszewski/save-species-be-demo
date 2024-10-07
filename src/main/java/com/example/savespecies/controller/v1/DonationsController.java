package com.example.savespecies.controller.v1;

import com.example.savespecies.dto.DonationDetailsListDto;
import com.example.savespecies.dto.DonationDto;
import com.example.savespecies.service.DonationsService;
import com.example.savespecies.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path="/v1/donations"
)
@RequiredArgsConstructor
@Log4j2
public class DonationsController {

    private final DonationsService donationsService;
    private final ValidationService validationService;

    @Operation(summary = "Add information about new donation for authenticated user")
    @PostMapping
    public ResponseEntity<Void> recordDonation(
            @RequestBody DonationDto donationDto
    ) {
        donationsService.recordDonation(donationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get list of all donations for authenticated user")
    @GetMapping
    public ResponseEntity<DonationDetailsListDto> getDonationsForUser() {
        return ResponseEntity.ok(donationsService.getDonationDetailsList());
    }

    @Operation(summary = "Delete donation by donationId for authenticated user")
    @DeleteMapping("/{donationId}")
    public ResponseEntity<Void> withdrawDonation(
            @PathVariable String donationId
    ) {
        validationService.validateDonationExists(donationId);
        donationsService.removeDonation(donationId);
        return ResponseEntity.noContent().build();
    }
}
