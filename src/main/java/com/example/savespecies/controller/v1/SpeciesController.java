package com.example.savespecies.controller.v1;

import com.example.savespecies.dto.SpeciesDto;
import com.example.savespecies.dto.SpeciesListDto;
import com.example.savespecies.service.SpeciesService;
import com.example.savespecies.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.example.savespecies.config.CacheConfig.ALL_SPECIES_CACHE;

@RestController
@RequestMapping(
        path="/v1/species"
)
@RequiredArgsConstructor
@Log4j2
public class SpeciesController {

    private final SpeciesService speciesService;
    private final ValidationService validationService;

    @Operation(summary = "Get given species. Required ADMIN role")
    @GetMapping("/{speciesName}")
    public ResponseEntity<SpeciesDto> getSpecies(
            @PathVariable String speciesName
    ) {
        Optional<SpeciesDto> speciesDto = speciesService.getSpecies(speciesName);
        if (speciesDto.isPresent()) {
            return ResponseEntity.ok(speciesDto.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Create given species. Required ADMIN role")
    @PutMapping()
    public ResponseEntity<Void> getSpecies(
            @RequestBody SpeciesDto speciesDto
    ) {
        speciesService.createSpecies(speciesDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Delete given species. Required ADMIN role")
    @DeleteMapping("/{speciesName}")
    public ResponseEntity<Void> deleteSpecies(
            @PathVariable String speciesName
    ) {
        validationService.validateSpeciesExists(speciesName);
        speciesService.deleteSpecies(speciesName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Get all species available. Method available for any authenticated user. Results are cached for 10 minutes")
    @GetMapping
    @Cacheable(
            value = ALL_SPECIES_CACHE
    )
    public ResponseEntity<SpeciesListDto> getAllSpecies() {
        return ResponseEntity.ok(speciesService.getAllSpecies());
    }
}
