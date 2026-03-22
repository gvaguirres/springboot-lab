package org.example.springbootlab.dto;

import org.example.springbootlab.form.Title;

public record CreateMovieDTO(

        @Title
        String title,
        String director,
        String year,
        String durationMinutes,
        String description) {}
