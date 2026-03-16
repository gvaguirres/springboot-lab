package org.example.springbootlab.form;

import org.example.springbootlab.dto.CreateMovieDTO;
import org.example.springbootlab.dto.UpdateMovieDTO;

public record MovieForm(

        @Title
        String title,
        String director,
        String year,
        int durationMinutes,
        String description) {

    public CreateMovieDTO toDTO() {
        return new CreateMovieDTO(
                this.title,
                this.director,
                this.year,
                this.durationMinutes,
                this.description);
    }

    public UpdateMovieDTO toUpdateDTO() {
        return new UpdateMovieDTO(
                this.title,
                this.director,
                this.year,
                this.durationMinutes,
                this.description);
    }
}
