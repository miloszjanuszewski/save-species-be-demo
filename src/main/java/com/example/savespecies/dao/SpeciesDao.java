package com.example.savespecies.dao;

import com.example.savespecies.dto.SpeciesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class SpeciesDao {

    private final DynamoDbTable<SpeciesEntry> dynamoDbTable;

    public Optional<SpeciesDto> getSpecies(String name) {
        Key partitionKey = Key.builder().partitionValue(name).build();
        SpeciesEntry speciesEntry = dynamoDbTable.getItem(partitionKey);
        return Optional.ofNullable(speciesEntry)
                       .map(this::convertToSpeciesDto);
    }

    public List<SpeciesDto> getAllSpecies() {
        return dynamoDbTable.scan()
                            .stream()
                            .flatMap(page -> page.items().stream())
                            .map(this::convertToSpeciesDto)
                            .toList();
    }


    public void createSpecies(SpeciesDto speciesDto) {
        dynamoDbTable.putItem(convertToSpeciesEntry(speciesDto));
    }

    public void deleteSpecies(String name) {
        Key partitionKey = Key.builder().partitionValue(name).build();
        dynamoDbTable.deleteItem(partitionKey);
    }

    private SpeciesDto convertToSpeciesDto(SpeciesEntry speciesEntry) {
        return new SpeciesDto(
                speciesEntry.getName(),
                speciesEntry.getCategories(),
                speciesEntry.getImagePath()
        );
    }

    private SpeciesEntry convertToSpeciesEntry(SpeciesDto speciesDto) {
        return SpeciesEntry.builder()
                           .name(speciesDto.name())
                           .categories(speciesDto.categories())
                           .imagePath(speciesDto.imagePath())
                           .build();
    }
}
