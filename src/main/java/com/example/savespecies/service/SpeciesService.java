package com.example.savespecies.service;

import com.example.savespecies.dao.SpeciesDao;
import com.example.savespecies.dto.SpeciesDto;
import com.example.savespecies.dto.SpeciesListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SpeciesService {

    private final SpeciesDao speciesDao;

    public Optional<SpeciesDto> getSpecies(String name) {
        return speciesDao.getSpecies(name);
    }

    public SpeciesListDto getAllSpecies() {
        return new SpeciesListDto(speciesDao.getAllSpecies());
    }

    public void createSpecies(SpeciesDto speciesDto) {
        speciesDao.createSpecies(speciesDto);
    }

    public void deleteSpecies(String name) {
        speciesDao.deleteSpecies(name);
    }
}
