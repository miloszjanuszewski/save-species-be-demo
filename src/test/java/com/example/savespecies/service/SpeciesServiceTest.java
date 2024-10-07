package com.example.savespecies.service;

import com.example.savespecies.dao.SpeciesDao;
import com.example.savespecies.dto.SpeciesListDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.savespecies.TestConstants.SPECIES_1;
import static com.example.savespecies.TestConstants.SPECIES_DTO;
import static com.example.savespecies.TestConstants.SPECIES_DTO_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class SpeciesServiceTest {

    @Mock
    private SpeciesDao speciesDao;

    @InjectMocks
    private SpeciesService underTest;

    @Test
    void shouldGetSpecies() {
        // given
        given(speciesDao.getSpecies(SPECIES_1))
                .willReturn(Optional.of(SPECIES_DTO));

        // when
        var result = underTest.getSpecies(SPECIES_1);

        // then
        assertThat(result).isEqualTo(Optional.of(SPECIES_DTO));
    }

    @Test
    void shouldGetAllSpecies() {
        // given
        given(speciesDao.getAllSpecies())
                .willReturn(List.of(
                        SPECIES_DTO,
                        SPECIES_DTO_2)
                );

        // when
        var result = underTest.getAllSpecies();

        // then
        assertThat(result).isEqualTo(
                new SpeciesListDto(
                        List.of(
                                SPECIES_DTO,
                                SPECIES_DTO_2
                        )
                )
        );
    }

    @Test
    void shouldCreateSpecies() {
        // when
        underTest.createSpecies(SPECIES_DTO);

        // then
        then(speciesDao).should().createSpecies(SPECIES_DTO);
    }

    @Test
    void shouldDeleteSpecies() {
        // when
        underTest.deleteSpecies(SPECIES_1);

        // then
        then(speciesDao).should().deleteSpecies(SPECIES_1);
    }
}
