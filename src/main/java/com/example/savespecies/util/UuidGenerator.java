package com.example.savespecies.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidGenerator {
    public String getUUID() {
        return UUID.randomUUID().toString();
    }
}
