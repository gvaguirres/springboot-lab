package org.example.springbootlab.dto;

public record UpdateMovieDTO(
        String title,
        String director,
        int durationMinutes,
        String genre) {}
