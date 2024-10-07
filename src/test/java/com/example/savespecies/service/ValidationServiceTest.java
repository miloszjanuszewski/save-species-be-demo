package com.example.savespecies.service;

import com.example.savespecies.exception.DonationNotFoundException;
import com.example.savespecies.exception.SpeciesNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.savespecies.TestConstants.DONATION_DETAILS_DTO;
import static com.example.savespecies.TestConstants.DONATION_ID;
import static com.example.savespecies.TestConstants.SPECIES_1;
import static com.example.savespecies.TestConstants.SPECIES_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @Mock
    private DonationsService donationsService;
    @Mock
    private SpeciesService speciesService;

    @InjectMocks
    private ValidationService underTest;


    @Nested
    class DonationExistsTests {
        @Test
        void shouldThrowWhenDonationDoesNotExists() {
            // given
            given(donationsService.getDonationDetails(DONATION_ID))
                    .willReturn(Optional.empty());

            // when
            Throwable caught = catchThrowable(() -> underTest.validateDonationExists(DONATION_ID));

            // then
            assertThat(caught).isExactlyInstanceOf(DonationNotFoundException.class);
        }

        @Test
        void shouldNotThrowWhenDonationExists() {
            // given
            given(donationsService.getDonationDetails(DONATION_ID))
                    .willReturn(Optional.of(DONATION_DETAILS_DTO));

            // when & then
            // no exception
            underTest.validateDonationExists(DONATION_ID);
        }
    }

    @Nested
    class SpeciesExistsTests {
        @Test
        void shouldThrowWhenSpeciesDoesNotExists() {
            // given
            given(speciesService.getSpecies(SPECIES_1))
                    .willReturn(Optional.empty());

            // when
            Throwable caught = catchThrowable(() -> underTest.validateSpeciesExists(SPECIES_1));

            // then
            assertThat(caught).isExactlyInstanceOf(SpeciesNotFoundException.class);
        }

        @Test
        void shouldNotThrowWhenSpeciesExists() {
            // given
            given(speciesService.getSpecies(SPECIES_1))
                    .willReturn(Optional.of(SPECIES_DTO));

            // when & then
            // no exception
            underTest.validateSpeciesExists(SPECIES_1);
        }
    }
}
