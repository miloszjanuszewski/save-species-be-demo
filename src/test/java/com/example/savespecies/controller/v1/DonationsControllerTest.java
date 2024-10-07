package com.example.savespecies.controller.v1;

import com.example.savespecies.dto.DonationDetailsListDto;
import com.example.savespecies.exception.ControllerExceptionHandler;
import com.example.savespecies.exception.DonationNotFoundException;
import com.example.savespecies.service.DonationsService;
import com.example.savespecies.service.ValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.savespecies.TestConstants.DONATION_DETAILS_DTO;
import static com.example.savespecies.TestConstants.DONATION_DETAILS_DTO_1;
import static com.example.savespecies.TestConstants.DONATION_DTO;
import static com.example.savespecies.TestConstants.DONATION_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DonationsControllerTest {

    @Mock
    private DonationsService donationsService;
    @Mock
    private ValidationService validationService;

    @InjectMocks
    private DonationsController underTest;

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                                 .setControllerAdvice(new ControllerExceptionHandler())
                                 .build();
    }

    @Nested
    class AddDonationTest {
        @Test
        void shouldAddDonation() throws Exception {
            // when & then
            mockMvc.perform(post("/v1/donations")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(DONATION_DTO)))
                   .andExpect(status().isCreated());

            then(donationsService).should().recordDonation(DONATION_DTO);
        }
    }

    @Nested
    class GetDonationsTest {
        @Test
        void shouldGetDonationsList() throws Exception {
            // given
            var donationDetailsListDto = new DonationDetailsListDto(
                    List.of(
                            DONATION_DETAILS_DTO,
                            DONATION_DETAILS_DTO_1
                    )
            );
            given(donationsService.getDonationDetailsList())
                    .willReturn(donationDetailsListDto);

            // when & then
            MvcResult mvcResult = mockMvc.perform(get("/v1/donations"))
                                         .andExpect(status().isOk())
                                         .andReturn();

            var result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), DonationDetailsListDto.class);
            assertThat(result).isEqualTo(donationDetailsListDto);
        }
    }

    @Nested
    class DeleteDonationsTest {
        @Test
        void shouldDeleteDonationById() throws Exception {
            // when & then
            mockMvc.perform(delete("/v1/donations/" + DONATION_ID))
                   .andExpect(status().isNoContent());

            then(donationsService).should().removeDonation(DONATION_ID);
        }

        @Test
        void shouldReturnNotFoundWhenNoDonationExist() throws Exception {
            // given
            doThrow(new DonationNotFoundException("boom"))
                    .when(validationService)
                    .validateDonationExists(DONATION_ID);

            // when & then
            mockMvc.perform(delete("/v1/donations/" + DONATION_ID))
                   .andExpect(status().isNotFound());

            then(donationsService).shouldHaveNoInteractions();
        }
    }
}
