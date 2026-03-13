package org.example.springbootlab.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@NotBlank
public record CreateMovieDTO(
        String title,
        String director,
        @Min(1)
        int durationMinutes,
        String genre) {}
