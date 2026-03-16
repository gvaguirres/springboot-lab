package org.example.springbootlab.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.example.springbootlab.form.Title;

@NotBlank
public record CreateMovieDTO(

        @Title
        String title,
        String director,
        String year,
        int durationMinutes,
        String description) {}
