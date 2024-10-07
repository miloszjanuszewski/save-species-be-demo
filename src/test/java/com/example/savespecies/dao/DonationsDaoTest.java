package com.example.savespecies.dao;

import com.example.savespecies.dto.DonationDetailsDto;
import com.example.savespecies.util.UuidGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.savespecies.TestConstants.AMOUNT_1;
import static com.example.savespecies.TestConstants.DONATION_DTO;
import static com.example.savespecies.TestConstants.DONATION_ID;
import static com.example.savespecies.TestConstants.SPECIES_1;
import static com.example.savespecies.TestConstants.USER_ID_1;
import static com.example.savespecies.TestConstants.UUID_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class DonationsDaoTest {

    @Mock
    private DynamoDbTable<DonationEntry> dynamoDbTable;
    @Mock
    private UuidGenerator uuidGenerator;

    @InjectMocks
    private DonationsDao underTest;

    private static final DonationEntry DONATION_ENTRY = DonationEntry.builder()
                                                                     .userId(USER_ID_1)
                                                                     .species(DONATION_DTO.species())
                                                                     .amount(DONATION_DTO.amount())
                                                                     .donationId(UUID_1)
                                                                     .build();
    private static final DonationDetailsDto DONATION_DETAILS_DTO = new DonationDetailsDto(
            UUID_1,
            USER_ID_1,
            SPECIES_1,
            AMOUNT_1
    );

    @Test
    void shouldInsertDonationEntry() {
        // given
        given(uuidGenerator.getUUID()).willReturn(UUID_1);

        // when
        underTest.insertDonation(DONATION_DTO, USER_ID_1);

        // then
        then(dynamoDbTable).should().putItem(DONATION_ENTRY);
    }

    @Test
    void shouldGetDonationDetails() {
        // given
        Key primaryKey = Key.builder()
                            .partitionValue(USER_ID_1)
                            .sortValue(DONATION_ID)
                            .build();
        given(dynamoDbTable.getItem(primaryKey)).willReturn(DONATION_ENTRY);

        // when
        var result = underTest.getDonationDetails(USER_ID_1, DONATION_ID);

        // then
        assertThat(result).isEqualTo(Optional.of(DONATION_DETAILS_DTO));
    }

    @Test
    void shouldGetDonationDetailsList() {
        // given
        Page<DonationEntry> page = Page.builder(DonationEntry.class)
                                       .count(1)
                                       .items(List.of(DONATION_ENTRY))
                                       .build();
        PageIterable<DonationEntry> pages = mock();
        given(pages.stream()).willReturn(Stream.of(page));
        given(dynamoDbTable.query(any(QueryConditional.class)))
                .willReturn(pages);

        // when
        var result = underTest.getDonationDetailsListForUser(USER_ID_1);

        // then
        assertThat(result).isEqualTo(
                List.of(DONATION_DETAILS_DTO)
        );
    }

    @Test
    void shouldDeleteDonation() {
        // given
        Key primaryKey = Key.builder()
                            .partitionValue(USER_ID_1)
                            .sortValue(DONATION_ID)
                            .build();

        // when
        underTest.deleteDonation(USER_ID_1, DONATION_ID);

        // then
        then(dynamoDbTable).should().deleteItem(primaryKey);
    }

}
