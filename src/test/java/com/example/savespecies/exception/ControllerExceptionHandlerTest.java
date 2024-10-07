package com.example.savespecies.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static com.example.savespecies.TestConstants.DONATION_ID;
import static com.example.savespecies.TestConstants.SPECIES_1;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ControllerExceptionHandlerTest {

    @InjectMocks
    private ControllerExceptionHandler underTest;

    @Test
    void shouldHandleDonationNotFoundException() {
        // when
        var result = underTest.handleDonationNotFound(
                new DonationNotFoundException(DONATION_ID)
        );

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldHandleSpeciesNotFoundException() {
        // when
        var result = underTest.handleDonationNotFound(
                new SpeciesNotFoundException(SPECIES_1)
        );

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
