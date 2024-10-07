package com.example.savespecies.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.savespecies.TestConstants.SPECIES_1;
import static com.example.savespecies.TestConstants.SPECIES_DTO;
import static com.example.savespecies.TestConstants.SPECIES_DTO_2;
import static com.example.savespecies.TestConstants.SPECIES_ENTRY_1;
import static com.example.savespecies.TestConstants.SPECIES_ENTRY_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SpeciesDaoTest {

    @Mock
    private DynamoDbTable<SpeciesEntry> dynamoDbTable;

    @InjectMocks
    private SpeciesDao underTest;

    @Test
    void shouldGetSpeciesByName() {
        // given
        Key partitionKey = Key.builder().partitionValue(SPECIES_1).build();
        given(dynamoDbTable.getItem(partitionKey))
                .willReturn(SPECIES_ENTRY_1);

        // when
        var result = underTest.getSpecies(SPECIES_1);

        // then
        assertThat(result).isEqualTo(
                Optional.of(SPECIES_DTO)
        );
    }

    @Test
    void shouldGetAllSpecies() {
        // given
        PageIterable<SpeciesEntry> pages = mock();
        Page<SpeciesEntry> page = Page.builder(SpeciesEntry.class)
                                      .count(2)
                                      .items(List.of(
                                              SPECIES_ENTRY_1,
                                              SPECIES_ENTRY_2
                                      ))
                                      .build();
        given(pages.stream()).willReturn(Stream.of(page));
        given(dynamoDbTable.scan()).willReturn(pages);

        // when
        var result = underTest.getAllSpecies();

        // then
        assertThat(result).isEqualTo(List.of(
                SPECIES_DTO,
                SPECIES_DTO_2
        ));
    }

    @Test
    void shouldCreateSpecies() {
        // when
        underTest.createSpecies(SPECIES_DTO);

        // then
        then(dynamoDbTable).should().putItem(SPECIES_ENTRY_1);
    }

    @Test
    void shouldDeleteSpecies() {
        // given
        Key partitionKey = Key.builder().partitionValue(SPECIES_1).build();

        // when
        underTest.deleteSpecies(SPECIES_1);

        // then
        then(dynamoDbTable).should().deleteItem(partitionKey);
    }
}
