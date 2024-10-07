package com.example.savespecies.dao;

import com.example.savespecies.dto.DonationDetailsDto;
import com.example.savespecies.dto.DonationDto;
import com.example.savespecies.util.UuidGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class DonationsDao {

    private final DynamoDbTable<DonationEntry> dynamoDbTable;
    private final UuidGenerator uuidGenerator;

    public void insertDonation(DonationDto donationDto, String userId) {
        DonationEntry donationEntry = DonationEntry.builder()
                                                   .userId(userId)
                                                   .species(donationDto.species())
                                                   .amount(donationDto.amount())
                                                   .donationId(uuidGenerator.getUUID())
                                                   .build();
        dynamoDbTable.putItem(donationEntry);
    }

    public Optional<DonationDetailsDto> getDonationDetails(String userId, String donationId) {
        Key primaryKey = Key.builder()
                            .partitionValue(userId)
                            .sortValue(donationId)
                            .build();
        DonationEntry donationEntry = dynamoDbTable.getItem(primaryKey);
        return Optional.ofNullable(donationEntry)
                       .map(this::convertToDonationDetailsDto);
    }

    public List<DonationDetailsDto> getDonationDetailsListForUser(String userId) {
        QueryConditional keyEqualUserId = QueryConditional.keyEqualTo(item -> item.partitionValue(userId));
        return dynamoDbTable.query(keyEqualUserId)
                            .stream()
                            .flatMap(donationEntryPage -> donationEntryPage.items().stream())
                            .map(this::convertToDonationDetailsDto)
                            .toList();
    }

    public void deleteDonation(String userId, String donationId) {
        Key donationRecordKey = Key.builder()
                                   .partitionValue(userId)
                                   .sortValue(donationId)
                                   .build();
        dynamoDbTable.deleteItem(donationRecordKey);
    }

    private DonationDetailsDto convertToDonationDetailsDto(DonationEntry donationEntry) {
        return new DonationDetailsDto(
                donationEntry.getDonationId(),
                donationEntry.getUserId(),
                donationEntry.getSpecies(),
                donationEntry.getAmount()
        );
    }
}
