package com.example.savespecies;

import com.example.savespecies.dao.SpeciesEntry;
import com.example.savespecies.dto.DonationDetailsDto;
import com.example.savespecies.dto.DonationDto;
import com.example.savespecies.dto.SpeciesDto;

import java.util.List;

public class TestConstants {
    public static final String DONATION_ID = "donationId1";
    public static final String DONATION_ID_2 = "donationId2";
    public static final String USER_ID_1 = "userId1";
    public static final String SPECIES_1 = "species1";
    public static final String SPECIES_2 = "species2";
    public static final String AMOUNT_1 = "100";
    public static final String SPECIES_CATEGORY_1 = "category1";
    public static final String SPECIES_CATEGORY_2 = "category2";
    public static final String IMAGE_URL = "imageUrl";
    public static final String UUID_1 = "uuid1";
    public static final List<String> SPECIES_CATEGORIES = List.of(
            SPECIES_CATEGORY_1,
            SPECIES_CATEGORY_2
    );
    public static final DonationDto DONATION_DTO = new DonationDto(
            SPECIES_1,
            AMOUNT_1
    );
    public static final DonationDetailsDto DONATION_DETAILS_DTO = new DonationDetailsDto(
            DONATION_ID,
            USER_ID_1,
            SPECIES_1,
            AMOUNT_1
    );
    public static final DonationDetailsDto DONATION_DETAILS_DTO_1 = new DonationDetailsDto(
            DONATION_ID_2,
            USER_ID_1,
            SPECIES_2,
            AMOUNT_1
    );
    public static final SpeciesDto SPECIES_DTO = new SpeciesDto(
            SPECIES_1,
            SPECIES_CATEGORIES,
            IMAGE_URL
    );
    public static final SpeciesDto SPECIES_DTO_2 = new SpeciesDto(
            SPECIES_2,
            SPECIES_CATEGORIES,
            IMAGE_URL
    );
    public static final SpeciesEntry SPECIES_ENTRY_1 = SpeciesEntry.builder()
                                                                   .name(SPECIES_1)
                                                                   .categories(SPECIES_CATEGORIES)
                                                                   .imagePath(IMAGE_URL)
                                                                   .build();
    public static final SpeciesEntry SPECIES_ENTRY_2 = SpeciesEntry.builder()
                                                                   .name(SPECIES_2)
                                                                   .categories(SPECIES_CATEGORIES)
                                                                   .imagePath(IMAGE_URL)
                                                                   .build();
}
