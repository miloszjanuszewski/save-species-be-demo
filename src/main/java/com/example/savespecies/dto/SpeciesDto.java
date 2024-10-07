package com.example.savespecies.dto;

import java.util.List;

public record SpeciesDto (
        String name,
        List<String> categories,
        String imagePath
) {}
