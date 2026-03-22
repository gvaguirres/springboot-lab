package org.example.springbootlab;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.example.springbootlab.exception.MovieAlreadyExistsException;
import org.example.springbootlab.form.MovieForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movies")
public class MovieWriteController {

    private static final Logger log = LoggerFactory.getLogger(MovieWriteController.class);
    private final MovieService movieService;

    private static final String MESSAGE = "message";
    private static final String REDIRECT_MOVIES_LIST = "redirect:/movies/list";
    public static final String MOVIE_FORM = "movieForm";

    public MovieWriteController(MovieService movieService) {
        log.info("MovieController constructor");
        this.movieService = movieService;
    }

    //Show formulary for a new movie
    @GetMapping("/new")
    public String showCreateForm(Model model){
        log.info("Showing form to create a new movie");
        model.addAttribute(MOVIE_FORM, new MovieForm(
                "", "", "", "", ""));
        return "form";
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute(MOVIE_FORM, new MovieForm("", "", "", "", ""));
        return "form";
    }

    //Creation of a movie
    @PostMapping("/create")
    public String createMovie(
            @Valid MovieForm movieForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        log.info("Create movie {}", movieForm);

        if(bindingResult.hasErrors()){
            log.error("Binding error ocurred");
            model.addAttribute(MOVIE_FORM, movieForm);
            model.addAttribute("nameError", "Please enter a title of a PuduMovie");
            model.addAttribute("errors", bindingResult);
            return "form";
        }

        try {
            movieService.createMovie(movieForm.toDTO());
        } catch (MovieAlreadyExistsException e){
            bindingResult.rejectValue("title", "error.movie.title", "PuduMovie already exists");
            model.addAttribute(MOVIE_FORM, movieForm);
            model.addAttribute("errors", bindingResult);
            return "form";
        }

        redirectAttributes.addFlashAttribute(MESSAGE, "PuduMovie created successfully" );

        return REDIRECT_MOVIES_LIST;
    }

    //Show formulary for update a movie
    @GetMapping("/update/{id}")
    public String showUpdateForm(Model model, @PathVariable Long id) {
        log.info("Showing form to update a new movie");

        var movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        model.addAttribute("id", id);
        model.addAttribute(MOVIE_FORM, new MovieForm(
                movie.getTitle(), movie.getDirector(), movie.getYear(), movie.getDurationMinutes(), movie.getDescription()));

        return "movie-edit-form";
    }

    //Update a movie
    @PostMapping("/update/{id}")
    public String updateMovie(
            @PathVariable Long id,
            @Valid @ModelAttribute("movieForm") MovieForm movieForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        log.info("Update movie {}", movieForm);

        if(bindingResult.hasErrors()){
            log.error("Binding error ocurred");
            model.addAttribute("id", id);
            var movie = movieService.getMovieById(id);
            model.addAttribute("movie", movie);

            return "movie-edit-form";
        }

        movieService.updateMovie(id, movieForm.toUpdateDTO());
        redirectAttributes.addFlashAttribute(MESSAGE, "PuduMovie updated successfully" );

        return REDIRECT_MOVIES_LIST;
    }

    //Delete a movie
    @PostMapping("/delete/{id}")
    public String deleteMovie(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        log.info("Delete movie {}", id);

        movieService.deleteMovie(id);
        redirectAttributes.addFlashAttribute(MESSAGE, "PuduMovie deleted successfully" );

        return REDIRECT_MOVIES_LIST;
    }
}
