package org.example.springbootlab.form;

import jakarta.validation.constraints.NotBlank;
import org.example.springbootlab.dto.CreateMovieDTO;
import org.example.springbootlab.dto.UpdateMovieDTO;

public record MovieForm(

        @Title
        @NotBlank(message = "Please enter a title of a PuduMovie")
        String title,
        String director,
        String year,
        String durationMinutes,
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
