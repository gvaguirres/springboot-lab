package org.example.springbootlab.dto;

public record UpdateMovieDTO(
        String title,
        String description,
        String year,
        String director,
        int durationMinutes) {}
