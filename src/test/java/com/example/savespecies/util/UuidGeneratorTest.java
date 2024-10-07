package com.example.savespecies.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class UuidGeneratorTest {

    @InjectMocks
    private UuidGenerator underTest;

    @Test
    void shouldReturnRandomUUID() {
        // when
        String result = underTest.getUUID();

        // then
        assertDoesNotThrow(() -> UUID.fromString(result));
    }
}
