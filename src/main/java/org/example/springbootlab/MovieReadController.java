package org.example.springbootlab;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
import org.example.springbootlab.dto.MovieDTO;
import org.example.springbootlab.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;


@Controller
@RequestMapping("/movies")
public class MovieReadController {

    private static final Logger log = LoggerFactory.getLogger(MovieReadController.class);
    private final MovieService movieService;

    private static final String MOVIE_PAGE = "moviePage";
    private static final String MOVIE_LIST = "movie-list";

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
    public String getMovies(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {

        log.info("MovieController getMovies");

        Sort sort = ascending ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<MovieDTO> moviePage = movieService.getAllBy(pageable);

        model.addAttribute(MOVIE_PAGE, moviePage);

        return MOVIE_LIST;
    }

    //Show a movie by a title
    @GetMapping("/title/{title}")
    public String getMovieByTitle(Model model, @PathVariable String title) {
        log.info("Get movie by title {}", title);

        List<MovieDTO> movies = movieService.getMovieByTitle(title);

        model.addAttribute(MOVIE_PAGE, new PageImpl<>(movies));

        return MOVIE_LIST;
    }

    //Show a list of movies with a certain director
    @GetMapping("/director/{director}")
    public String getMoviesByDirector(Model model, @PathVariable String director) {
        log.info("Get movies by a certain director {}", director);

        List<MovieDTO> movies = movieService.getMoviesByDirector(director);

        model.addAttribute(MOVIE_PAGE, new PageImpl<>(movies));

        return MOVIE_LIST;
    }

    //Show a list of movies with a certain year
    @GetMapping("/year/{year}")
    public String getMoviesByYear(
            Model model,
            @PathVariable String year) {

        log.info("Get movies by a certain year {}", year);

        List<MovieDTO> movies = movieService.getMoviesByYear(year);

        model.addAttribute(MOVIE_PAGE, new PageImpl<>(movies));

        return MOVIE_LIST;
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
                case "title" -> {return "redirect:/movies/title/" + query;}
                case "director" -> {return "redirect:/movies/director/" + query;}
                case "year" -> {return "redirect:/movies/year/" + query;}
                default -> {return "home";}
            }
        } catch (ResourceNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "home";
        }
    }
}
