package org.example.springbootlab.form;

import org.example.springbootlab.dto.CreateMovieDTO;
import org.example.springbootlab.dto.UpdateMovieDTO;

public record MovieForm(

        @Title
        String title,

        String director,
        String description,
        String year,
        int durationMinutes) {

    public CreateMovieDTO toDTO() {
        return new CreateMovieDTO(
                this.title,
                this.director,
                this.description,
                this.year,
                this.durationMinutes);
    }

    public UpdateMovieDTO toUpdateDTO() {
        return new UpdateMovieDTO(
                this.title,
                this.director,
                this.description,
                this.year,
                this.durationMinutes);
    }
}
