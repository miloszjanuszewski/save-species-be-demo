package com.example.savespecies.controller.v1;

import com.example.savespecies.dto.SpeciesDto;
import com.example.savespecies.dto.SpeciesListDto;
import com.example.savespecies.exception.ControllerExceptionHandler;
import com.example.savespecies.exception.SpeciesNotFoundException;
import com.example.savespecies.service.SpeciesService;
import com.example.savespecies.service.ValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static com.example.savespecies.TestConstants.SPECIES_1;
import static com.example.savespecies.TestConstants.SPECIES_DTO;
import static com.example.savespecies.TestConstants.SPECIES_DTO_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SpeciesControllerTest {

    @Mock
    private SpeciesService speciesService;
    @Mock
    private ValidationService validationService;

    @InjectMocks
    private SpeciesController underTest;

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                                 .setControllerAdvice(new ControllerExceptionHandler())
                                 .build();
    }

    @Nested
    class GetSpeciesTest {
        @Test
        void shouldGetSpeciesWhenExist() throws Exception {
            // given
            given(speciesService.getSpecies(SPECIES_1))
                    .willReturn(Optional.of(SPECIES_DTO));

            // when & then
            MvcResult mvcResult = mockMvc.perform(get("/v1/species/" + SPECIES_1))
                                         .andExpect(status().isOk())
                                         .andReturn();

            var result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SpeciesDto.class);
            assertThat(result).isEqualTo(SPECIES_DTO);
        }

        @Test
        void shouldGetNotFoundWhenSpeciesDoNotExist() throws Exception {
            // given
            given(speciesService.getSpecies(SPECIES_1))
                    .willReturn(Optional.empty());

            // when & then
            mockMvc.perform(get("/v1/species/" + SPECIES_1))
                   .andExpect(status().isNoContent());
        }
    }

    @Nested
    class AddSpeciesTest {
        @Test
        void shouldAddGivenSpecies() throws Exception{
            // when & then
            mockMvc.perform(put("/v1/species")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(SPECIES_DTO)))
                    .andExpect(status().isCreated());

            then(speciesService).should().createSpecies(SPECIES_DTO);
        }
    }

    @Nested
    class DeleteSpeciesTest {
        @Test
        void shouldDeleteGivenSpeciesWhenExist() throws Exception {
            // when & then
            mockMvc.perform(delete("/v1/species/" + SPECIES_1))
                   .andExpect(status().isNoContent());

            then(speciesService).should().deleteSpecies(SPECIES_1);
        }

        @Test
        void shouldReturnNoContent() throws Exception {
            // given
            doThrow(new SpeciesNotFoundException("boom"))
                    .when(validationService)
                    .validateSpeciesExists(SPECIES_1);

            // when & then
            mockMvc.perform(delete("/v1/species/" + SPECIES_1))
                   .andExpect(status().isNotFound());

            then(speciesService).shouldHaveNoInteractions();
        }
    }

    @Nested
    class GetAllSpeciesTest {
        @Test
        void shouldReturnAllSpecies() throws Exception {
            // given
            var speciesListDto = new SpeciesListDto(
                    List.of(
                            SPECIES_DTO,
                            SPECIES_DTO_2
                    )
            );
            given(speciesService.getAllSpecies())
                    .willReturn(speciesListDto);

            // when & then
            MvcResult mvcResult = mockMvc.perform(get("/v1/species"))
                                         .andExpect(status().isOk())
                                         .andReturn();

            var result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SpeciesListDto.class);
            assertThat(result).isEqualTo(speciesListDto);
        }
    }
}
