package org.example.springbootlab;

import jakarta.validation.Valid;
import org.example.springbootlab.exception.MovieAlreadyExistsException;
import org.example.springbootlab.form.MovieForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MovieWriteController {

    private static final Logger log = LoggerFactory.getLogger(MovieReadController.class);

    private final MovieService movieService;

    public MovieWriteController(MovieService movieService) {
        log.info("MovieController constructor");
        this.movieService = movieService;
    }

    //Show formulary for a new movie
    @GetMapping("/new")
    public String showCreateForm(Model model){
        log.info("Showing form to create a new movie");
        model.addAttribute("movieForm", new MovieForm(
                "", "", "", "", ""));
        return "form";
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("movieForm", new MovieForm("", "", "", "", ""));
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
            model.addAttribute("movieForm", movieForm);
            model.addAttribute("nameError", "Please enter a title of a PuduMovie");
            model.addAttribute("errors", bindingResult);
            return "form";
        }

        try {
            movieService.createMovie(movieForm.toDTO());
        } catch (MovieAlreadyExistsException e){
            bindingResult.rejectValue("title", "error.movie.title", "PuduMovie already exists");
            model.addAttribute("movieForm", movieForm);
            model.addAttribute("errors", bindingResult);
            return "form";
        }

        redirectAttributes.addFlashAttribute("message", "PuduMovie created successfully" );

        return "redirect:/movies/list";
    }

    //Show formulary for update a movie
    @GetMapping("/update/{id}")
    public String showUpdateForm(Model model, @PathVariable Long id) {
        log.info("Showing form to update a new movie");

        var movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        model.addAttribute("id", id);
        model.addAttribute("movieForm", new MovieForm(
                movie.getTitle(), movie.getDirector(), movie.getYear(), movie.getDurationMinutes(), movie.getDescription()));

        return "movie-edit-form";
    }

    //Update a movie
    @PostMapping("/update/{id}")
    public String updateMovie(
            @PathVariable Long id,
            @Valid @ModelAttribute("movieForm") MovieForm movieForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        log.info("Update movie {}", movieForm);

        if(bindingResult.hasErrors()){
            log.error("Binding error ocurred");
            return "movie-edit-form";
        }

        movieService.updateMovie(id, movieForm.toUpdateDTO());
        redirectAttributes.addFlashAttribute("message", "PuduMovie updated successfully" );

        return "redirect:/movies/list";
    }

    //Delete a movie
    @PostMapping("/delete/{id}")
    public String deleteMovie(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        log.info("Delete movie {}", id);

        movieService.deleteMovie(id);
        redirectAttributes.addFlashAttribute("message", "PuduMovie deleted successfully" );

        return "redirect:/movies/list";
    }
}
