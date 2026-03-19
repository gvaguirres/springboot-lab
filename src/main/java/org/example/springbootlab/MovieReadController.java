package org.example.springbootlab;

import jakarta.validation.Valid;
import org.example.springbootlab.exception.MovieAlreadyExistsException;
import org.example.springbootlab.exception.ResourceNotFoundException;
import org.example.springbootlab.form.MovieForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/movies")
public class MovieReadController {

    private static final Logger log = LoggerFactory.getLogger(MovieReadController.class);

    private final MovieService movieService;

    public MovieReadController(MovieService movieService) {
        log.info("MovieController constructor");
        this.movieService = movieService;
    }

    @GetMapping
    public String showHomePage() {
        return "home";
    }

    //Show a list with all the movies
    @GetMapping("/list")
    public String getMovies(Model model) {
        log.info("MovieController getMovies");

        model.addAttribute("movies", movieService.getAllMovies());
        return "movie-list";
    }

    //Show a movie with a certain id
    @GetMapping("/{id}")
    public String getMovieById(Model model, @PathVariable Long id) {
        log.info("Get movie with id {}", id);
        model.addAttribute("movies", movieService.getMovieById(id));
        return "movie-details";
    }

    //Show a movie by a title
    @GetMapping("/title/{title}")
    public String getMovieByTitle(Model model, @PathVariable String title) {
        log.info("Get movie by title {}", title);
        model.addAttribute("movies", movieService.getMovieByTitle(title));
        return "movie-list";
    }

    //Show a list of movies with a certain director
    @GetMapping("/director/{director}")
    public String getMovieByDirector(Model model, @PathVariable String director) {
        log.info("Get movies by a certain director {}", director);
        model.addAttribute("movies", movieService.getMovieByDirector(director));
        return "movie-list";
    }

    //Show a list of movies with a certain year
    @GetMapping("/year/{year}")
    public String getMoviesByYear(Model model, @PathVariable String year) {
        log.info("Get movies by a certain year {}", year);
        model.addAttribute("movies", movieService.getMovieByYear(year));
        return "movie-list";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam String query,
            @RequestParam String searchType,
            Model model){

        try {
            if (query == null || query.isEmpty()) {
                return "redirect:/";
            }

            switch (searchType) {
                case "title" -> {
                    return "redirect:/movies/title/" + query;
                }
                case "director" -> {
                    return "redirect:/movies/director/" + query;
                }
                case "year" -> {
                    return "redirect:/movies/year/" + query;
                }
            }
        } catch (ResourceNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "home";
        }
        return "home";
    }
}
