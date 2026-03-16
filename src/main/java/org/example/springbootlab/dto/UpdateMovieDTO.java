package org.example.springbootlab.dto;

public record UpdateMovieDTO(
        String title,
        String director,
        String year,
        int durationMinutes,
        String description) {}
