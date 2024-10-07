package com.example.savespecies.exception;

public class SpeciesNotFoundException extends RuntimeException {
    public SpeciesNotFoundException(String speciesName) {
        super(String.format("Species name not found %s", speciesName));
    }
}
