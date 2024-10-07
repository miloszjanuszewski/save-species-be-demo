package com.example.savespecies.service;

import com.example.savespecies.dao.DonationsDao;
import com.example.savespecies.dto.DonationDetailsListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static com.example.savespecies.TestConstants.DONATION_DETAILS_DTO;
import static com.example.savespecies.TestConstants.DONATION_DTO;
import static com.example.savespecies.TestConstants.DONATION_ID;
import static com.example.savespecies.TestConstants.USER_ID_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class DonationServiceTest {

    @Mock
    private DonationsDao donationsDao;

    @InjectMocks
    private DonationsService underTest;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(USER_ID_1, null)
        );
    }

    @Test
    void shouldRecordDonationWithAuthenticatedUser() {
        // when
        underTest.recordDonation(DONATION_DTO);

        // then
        then(donationsDao).should().insertDonation(
                DONATION_DTO,
                USER_ID_1
        );
    }

    @Test
    void shouldGetDonationDetailsWithAuthenticatedUser() {
        // given
        given(donationsDao.getDonationDetails(USER_ID_1, DONATION_ID))
                .willReturn(Optional.of(DONATION_DETAILS_DTO));

        // when
        var result = underTest.getDonationDetails(DONATION_ID);

        // then
        assertThat(result).isEqualTo(
                Optional.of(DONATION_DETAILS_DTO)
        );
    }

    @Test
    void shouldGenDonationDetailsListWithAuthenticatedUser() {
        // given
        given(donationsDao.getDonationDetailsListForUser(USER_ID_1))
                .willReturn(List.of(DONATION_DETAILS_DTO));

        // when
        var result = underTest.getDonationDetailsList();

        // then
        assertThat(result).isEqualTo(
                new DonationDetailsListDto(
                        List.of(DONATION_DETAILS_DTO)
                )
        );
    }

    @Test
    void shouldRemoveDonationWithAuthenticatedUser() {
        // when
        underTest.removeDonation(DONATION_ID);

        // then
        then(donationsDao).should().deleteDonation(
                USER_ID_1,
                DONATION_ID
        );
    }
}
